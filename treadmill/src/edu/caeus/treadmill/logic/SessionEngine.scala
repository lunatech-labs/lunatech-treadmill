package edu.caeus.treadmill.logic

import edu.caeus.treadmill.algebra.{Credentials, SessionID}

import scala.concurrent.Future

class SessionEngine {
  def create(c: Credentials): Future[SessionID] = {
    Future.successful(SessionID())
  }

}
