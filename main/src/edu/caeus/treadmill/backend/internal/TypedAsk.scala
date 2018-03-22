package edu.caeus.treadmill.backend.internal

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.reflect.ClassTag

trait TypedAsk[T] {
  def askTo(actorRef: ActorRef)(implicit classTag: ClassTag[T], timeout: Timeout): Future[T] = {
    (actorRef ? this).mapTo[T]
  }
}