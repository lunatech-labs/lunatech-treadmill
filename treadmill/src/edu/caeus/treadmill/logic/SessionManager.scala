package edu.caeus.treadmill.logic

import akka.http.scaladsl.model.HttpRequest
import edu.caeus.treadmill.algebra.User

import scala.concurrent.Future

class SessionManager {

  def authenticated(req:HttpRequest):Future[User] = ???

}
