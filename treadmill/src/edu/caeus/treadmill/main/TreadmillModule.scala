package edu.caeus.treadmill.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import edu.caeus.treadmill.controllers.SheetCtrl
import edu.caeus.treadmill.logic.SheetEngine
import edu.caeus.treadmill.routes.Routes
import edu.caeus.treadmill.util.AppTerminator

import scala.concurrent.ExecutionContext


class TreadmillModule {




  implicit lazy val terminator = new AppTerminator

  import terminator._

  implicit lazy val actorSystem: ActorSystem = ActorSystem()
    .closeAsyncWith(_.terminate())

  implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher

  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  lazy val sheetEngine = new SheetEngine()

  lazy val sheetCtrl = new SheetCtrl(sheetEngine)

  lazy val httpRoutes = Routes(sheetCtrl)

  lazy val eventualHttpBinding = Http().bindAndHandle(httpRoutes, "localhost", 9000)
    .closeAsyncWith(_.flatMap(_.unbind()))

  def start() = ()


}


object TreadmillModule {
  def start() = {
    new TreadmillModule().start()
  }
}


