package edu.caeus.treadmill.logic

import edu.caeus.treadmill.algebra.SheetOp
import edu.caeus.treadmill.logic.SheetOpEngine.Res

import scala.concurrent.Future

class SheetOpEngine() {


  class Inner(sheetId: String) {

    def create(seed: SheetOp.Seed): Res[SheetOp.Ref] = ???

    def query: Res[Seq[SheetOp.Ref]] = ???

    def get(id: String): Res[Option[SheetOp]] = ???


  }

  def apply(id: String) = new Inner(id)

}

object SheetOpEngine {
  type Res[+T] = Future[T]
}