package edu.caeus.treadmill.routes

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import edu.caeus.treadmill.controllers.SheetCtrl

import scala.concurrent.Future

/**
  * Automatic to and from JSON marshalling/unmarshalling using *upickle* protocol.
  */
object Routes {
  @inline
  private def handleReq(body: HttpRequest => Future[HttpResponse]): Route = {
    extract(_.request) { req =>
      complete(body(req))
    }
  }

  def apply(sheetCtrl: SheetCtrl): Route = {
    pathPrefix("v1") {
      pathPrefix("sheet") {
        (pathEnd & post) {
          handleReq(sheetCtrl.create)
        } ~
          (path(Segment) & get) { sheetId =>
            handleReq(sheetCtrl.byId(sheetId))
          } ~
          post {
            complete("hola")
          }
      }

    }
  }


}
