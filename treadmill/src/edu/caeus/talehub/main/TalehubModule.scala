package edu.caeus.talehub.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import edu.caeus.talehub.routes.Routes
import edu.caeus.talehub.util.AppTerminator

import scala.concurrent.ExecutionContext


class TalehubModule {

  implicit lazy val terminator = new AppTerminator

  import terminator._

  implicit lazy val actorSystem: ActorSystem = ActorSystem()
    .closeAsyncWith(_.terminate())
  implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher

  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()

  implicit lazy val httpRoutes = Routes()


  lazy val eventualHttpBinding = Http().bindAndHandle(httpRoutes, "localhost", 9000)
    .closeAsyncWith(_.flatMap(_.unbind()))

  def start() = ()


}

object TalehubModule {
  def start() = {
    new TalehubModule().start()
  }
}


