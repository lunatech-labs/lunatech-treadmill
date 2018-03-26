package edu.caeus.treadmill.backend.internal

import akka.actor.{Actor, Props}
import cats.Id
import cats.arrow.FunctionK
import cats.free.Free
import edu.caeus.treadmill.backend.internal.GitRepoTransactor._

trait GitRepoTransactor {
  def show(file: String, version: Option[String]): String = ???

  def add(file: String): Unit = ???

  def commit: Unit = ???

  def touch(file: String): Boolean = ???

}

class GitRepoTransactorActor extends Actor with GitRepoTransactor {
  transactor =>


  val interpreter = new FunctionK[Alg, Id] {
    override def apply[A](fa: Alg[A]): Id[A] = {
      fa.exec(transactor)
    }
  }

  override def receive: Receive = {
    case Tx(op) =>
      sender() ! op.foldMap(interpreter)
  }
}

object GitRepoTransactor {

  case class Tx[T](op: Op[T]) extends TypedAsk[T]



  type Op[T] = Free[Alg, T]

  sealed trait Alg[T] {
    def exec(on: GitRepoTransactor): T
  }

  case class Touch(file: String) extends Alg[Boolean] {
    override def exec(on: GitRepoTransactor): Boolean =
      on.touch(file)
  }

  case object Commit extends Alg[Unit] {
    override def exec(on: GitRepoTransactor): Unit = on.commit
  }

  case class Add(file: String) extends Alg[Unit] {
    override def exec(on: GitRepoTransactor): Unit = on.add(file)
  }

  case class Show(file: String, version: Option[String]) extends Alg[String] {
    override def exec(on: GitRepoTransactor): String = on.show(file,version)
  }

  def props = Props(new GitRepoTransactorActor)
}