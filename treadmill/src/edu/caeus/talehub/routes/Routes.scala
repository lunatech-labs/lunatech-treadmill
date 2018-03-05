package edu.caeus.talehub.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes {
  def apply(): Route = {
    path("v1") {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hola!"))
      }
    }
  }

}
