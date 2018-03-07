package edu.caeus.treadmill.controllers

import akka.http.scaladsl.model._
import akka.stream.Materializer
import cats.data.Kleisli
import edu.caeus.treadmill.algebra.User
import edu.caeus.treadmill.logic.{SessionManager, SheetEngine}
import edu.caeus.treadmill.util.json.UpickleSupport
import upickle.default._

import scala.concurrent.{ExecutionContext, Future}

class SheetCtrl(sheetEngine: SheetEngine, sessionManager: SessionManager)
               (implicit executionContext: ExecutionContext, materializer: Materializer) {


  private def doReq[I: Reader, O: Writer](req: HttpRequest)(func: I => Kleisli[Future, User, O]): Future[HttpResponse] = {
    for {
      user <- sessionManager.authenticated(req)
      ibody <- UpickleSupport.unmarshaller[I].apply(req.entity)
      obody <- func(ibody).run(user)
    } yield
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, write(obody)))
  }

  def create(req: HttpRequest): Future[HttpResponse] = {
    doReq(req)(sheetEngine.create)


  }

  def update(id: String)(req: HttpRequest): Future[HttpResponse] = ???

  def byId(id: String)(req: HttpRequest): Future[HttpResponse] = ???
}
