package edu.caeus.treadmill.controllers

import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse}
import akka.stream.Materializer
import edu.caeus.treadmill.algebra.failures.{BadRequestException, UnauthenticatedException}
import edu.caeus.treadmill.util.json.UpickleSupport
import upickle.default._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

object Ctrl {


  implicit class HttpEntityOps(private val entity: HttpEntity) extends AnyVal {
    def bodyAs[B: Reader](implicit executionContext: ExecutionContext,materializer: Materializer): Future[B] = {
      UpickleSupport.unmarshaller[B].apply(entity).recoverWith {
        case NonFatal(e) =>
          Future.failed(BadRequestException(Some(e)))
      }
    }
  }

  implicit class HttpRequestOps(private val request: HttpRequest) extends AnyVal {
    def bodyAs[B: Reader](implicit executionContext: ExecutionContext,materializer: Materializer): Future[B] = {
      request.entity.bodyAs[B]
    }


  }

  implicit class HttpResponseOps(private val value: HttpResponse) extends AnyVal {

  }

  object Responses {
    val Ok: HttpResponse = ???

  }


}
