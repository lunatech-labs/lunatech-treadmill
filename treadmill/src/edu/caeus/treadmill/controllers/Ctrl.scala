package edu.caeus.treadmill.controllers

import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse}
import edu.caeus.treadmill.algebra.SessionID
import edu.caeus.treadmill.algebra.failures.{BadRequestException, UnauthenticatedException}
import edu.caeus.treadmill.util.json.UpickleSupport
import upickle.default._

import scala.concurrent.Future
import scala.util.control.NonFatal

object Ctrl {


  implicit class HttpEntityOps(private val entity: HttpEntity) extends AnyVal {
    def bodyAs[B: Reader]: Future[B] = {
      UpickleSupport.unmarshaller[B].apply(entity).recoverWith {
        case NonFatal(e) =>
          Future.failed(BadRequestException(Some(e)))
      }
    }
  }

  implicit class HttpRequestOps(private val request: HttpRequest) extends AnyVal {
    def bodyAs[B: Reader]: Future[B] = {
      request.entity.bodyAs[B]
    }

    def sessionID: Option[SessionID] = ???

    final def withSessionID: Future[SessionID] = sessionID.map(Future.successful)
      .getOrElse(Future.failed(UnauthenticatedException()))
  }

  implicit class HttpResponseOps(private val value: HttpResponse) extends AnyVal {
    def withSession(cookie: SessionID): HttpResponse = {
      ???
    }
  }

  object Responses {
    val Ok: HttpResponse = ???

  }


}
