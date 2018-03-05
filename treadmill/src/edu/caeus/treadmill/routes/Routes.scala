package edu.caeus.treadmill.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import edu.caeus.treadmill.algebra.Sheet
import edu.caeus.treadmill.util.json.UpickleSupport

/**
  * Automatic to and from JSON marshalling/unmarshalling using *upickle* protocol.
  */


object Routes {

  import UpickleSupport._

  def apply(): Route = {
    pathPrefix("v1") {
      post {
        entity(as[Sheet]) {
          sheet =>
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hola!"))
        }

      }
    }
  }

}
