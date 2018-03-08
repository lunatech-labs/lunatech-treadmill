package edu.caeus.treadmill.logic

import edu.caeus.treadmill.algebra._
import edu.caeus.treadmill.logic.SheetEngine.Res
import upickle.Js

import scala.concurrent.Future

class SheetEngine {


  def create(id: String)(body: Js.Value): Res[SheetRef] = { sessionId =>
    Future.successful(SheetRef())
  }

  def query: Res[Seq[SheetRef]] = {
    ???
  }

  def byId(id: String): Res[Option[Sheet]] = ???

  def update(id: String)(op: SheetOp): Res[SheetRef] = ???

}

object SheetEngine {
  type Res[+T] = SessionID => Future[T]
}
