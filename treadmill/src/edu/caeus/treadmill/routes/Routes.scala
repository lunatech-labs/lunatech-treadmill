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
      pathPrefix("sheets") {
        (path(Segment) & put) { sheetId =>

          handleReq(sheetCtrl.create(sheetId))
        } ~
          (path(Segment) & get) { sheetId =>
            handleReq(sheetCtrl.byId(sheetId))
          } ~
          (path(Segment / "updates") & post) { sheetId =>
            handleReq(sheetCtrl.update(sheetId))
          }
      }
    }
  }


}
