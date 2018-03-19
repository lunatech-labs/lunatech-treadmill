package edu.caeus.treadmill.logic

import edu.caeus.treadmill.algebra._
import edu.caeus.treadmill.logic.SheetEngine.Res

import scala.concurrent.Future

class SheetEngine {


  def create(seed: Sheet.Seed): Res[Sheet.Ref] = {
    Future.successful(Sheet.Ref(seed.id))
  }

  def query: Res[Seq[Sheet.Ref]] = {
    Future.successful(Nil)

  }

  def byId(id: String): Res[Option[Sheet]] = {
    Future.successful(None)
  }

  def ops(id: String)(op: SheetOp): Res[Sheet.Ref] = ???

}

object SheetEngine {
  type Res[+T] = Future[T]
}
