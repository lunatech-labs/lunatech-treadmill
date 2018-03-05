package edu.caeus.talehub.util

import java.util.concurrent.atomic.AtomicReference
import java.util.function.UnaryOperator

import scala.concurrent.{Future, Promise}
import scala.util.control.NonFatal
import scala.concurrent.ExecutionContext.Implicits.global
class AppTerminator {
  terminator =>

  private object log {
    def fatal(str: String) = ()

  }

  private val terminationTrigger = Promise[Option[Throwable]]()
  private val lastCallback = Promise[Option[Throwable]]()
  private val termination: Future[Unit] = lastCallback.future.flatMap {
    case Some(e) => Future.failed(e)
    case None => Future.successful(Unit)
  }
  private val backwardsTrigger = new AtomicReference[Option[Promise[Option[Throwable]]]](Some(lastCallback))

  terminationTrigger.future.onComplete(
    triedMaybeThrowable =>
      backwardsTrigger.getAndSet(None) match {
        case Some(realTrigger) =>
          realTrigger.success(triedMaybeThrowable.toEither.fold(Some(_), identity))
        case None =>
          log.fatal("No need to verify this as onComplete is only called once")
      }
  )

  /**
    * Request to terminate all defined resources.
    *
    * @return termination future.
    */
  def terminate(withFailure: Option[Throwable] = None): Future[Unit] = {
    terminationTrigger.trySuccess(withFailure)
    termination
  }

  /**
    * Registers a callback to get called when calling `terminate`.
    *
    * @param cb callback to be invoked.
    */
  def registerCallback(cb: () => Future[Unit]): Unit = {
    def callSafely(cb: () => Future[Unit]): Future[Option[Throwable]] =
      try {
        cb().map(_ => None)
          .recover {
            case NonFatal(e) =>
              Some(e)
          }
      } catch {
        case NonFatal(e) =>
          Future.successful(Some(e))
      }

    backwardsTrigger.updateAndGet(new UnaryOperator[Option[Promise[Option[Throwable]]]] {
      override def apply(maybeOldCallback: Option[Promise[Option[Throwable]]]): Option[Promise[Option[Throwable]]] =
        maybeOldCallback match {
          case None =>
            // Or should fail?
            callSafely(cb)
            None
          case Some(oldCallback) =>
            val newCallback = Promise[Option[Throwable]]()
            oldCallback.tryCompleteWith(newCallback.future.flatMap { _ =>
              callSafely(cb)
            })
            Some(newCallback)
        }
    })

  }

  /**
    * Termination future.
    *
    * @return termination future.
    */
  def whenTerminate: Future[Unit] = termination

  implicit class TerminationResourceOps[T](private val resource: T) {

    /**
      * Given a resource, specify how to close it.
      *
      * @param closer function that know how to close the resource T.
      * @return the resource being considered.
      */
    def closeWith(closer: T => Unit): T = {
      val forced = resource
      terminator.registerCallback { () =>
        Future {
          closer(forced)
          Unit
        }
      }
      forced
    }

    /**
      * Given a resource, specify how to close it (Used when the resource is closed with a Future).
      *
      * @param closer function that know how to close the resource T.
      * @return the resource being considered.
      */
    def closeAsyncWith(closer: T => Future[Any]): T = {
      val forced = resource
      terminator.registerCallback { () =>
        closer(forced)
          .map(_ => Unit)
      }
      forced
    }
  }


}