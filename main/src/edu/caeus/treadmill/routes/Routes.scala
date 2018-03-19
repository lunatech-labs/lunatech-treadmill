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
      (get & path("sheets.refs")) {
        handleReq(sheetCtrl.query)
      } ~
        (get & path("sheets" / Segment)) {
          sheetId =>
            handleReq(sheetCtrl.byId(sheetId))
        } ~
        (post & path("sheets" /"$set")) {
            handleReq(sheetCtrl.set)
        } ~
        pathPrefix("sheets" / Segment) {
          sheetId =>
            (get & path("ops.refs")) {
              ???
            } ~
              (get & path("ops" / Segment)) { opId =>
                ???
              } ~
              (post & path("ops"/"$submit")) {
                ???
              }
        }

    }
  }


}
