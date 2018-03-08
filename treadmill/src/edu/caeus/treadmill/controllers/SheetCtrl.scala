package edu.caeus.treadmill.controllers

import akka.http.scaladsl.model._
import akka.stream.Materializer
import edu.caeus.treadmill.controllers.Ctrl._
import edu.caeus.treadmill.logic.SheetEngine
import edu.caeus.treadmill.logic.SheetEngine.Res
import upickle.default._

import scala.concurrent.{ExecutionContext, Future}

class SheetCtrl(sheetEngine: SheetEngine)
               (implicit executionContext: ExecutionContext, materializer: Materializer) {


  @inline
  private def withBody[I: Reader, O: Writer](req: HttpRequest)(func: I => Res[O]): Future[HttpResponse] = {

    for {
      sessionId <- req.withSessionID
      ibody <- req.entity.bodyAs[I]
      obody <- func(ibody)(sessionId)
    } yield
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, write(obody)))


  }

  def create(id: String)(req: HttpRequest): Future[HttpResponse] = {
    withBody(req)(sheetEngine.create(id))

  }

  def update(id: String)(req: HttpRequest): Future[HttpResponse] = {
    withBody(req)(sheetEngine.update(id))
  }

  def query(req: HttpRequest): Future[HttpResponse] = {
    for {
      user <- req.withSessionID
      obody <- sheetEngine.query(user)
    } yield
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, write(obody)))
  }

  def byId(id: String)(req: HttpRequest): Future[HttpResponse] = {
    for {
      user <- req.withSessionID
      obody <- sheetEngine.byId(id)(user).flatMap {
        case Some(body) => Future.successful(body)
        case None => Future.failed(ResourceNotFoundException())
      }
    } yield
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, write(obody)))
  }
}
