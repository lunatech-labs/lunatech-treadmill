package edu.caeus.treadmill.controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import edu.caeus.treadmill.algebra.Credentials
import edu.caeus.treadmill.controllers.Ctrl._
import edu.caeus.treadmill.logic.SessionEngine

import scala.concurrent.Future

class SessionCtrl(sessionEngine: SessionEngine) {


  def create(req: HttpRequest): Future[HttpResponse] = {
    req.entity.bodyAs[Credentials].flatMap(sessionEngine.create)
      .map(Ctrl.Responses.Ok.withSession)
  }
}
