package edu.caeus.treadmill.backend

import cats.data.Kleisli
import edu.caeus.treadmill.backend.SheetsRegistry.Res
import edu.caeus.treadmill.backend.data.SheetOp.Seed
import edu.caeus.treadmill.backend.data._

import scala.concurrent.Future
import scala.util.Try


object SheetsRegistry {
  type Res[T] = Kleisli[Future, ValidID, T]
}

class SheetsRegistry {


  def query: Res[Seq[Sheet.Ref]] = ???

  def set(sheet: Sheet): Res[Sheet.Ref] = ???

  def get(id: ValidID, op: Option[SheetOpID] = None): Res[Option[Sheet]] = ???

  def ops(id: ValidID): SheetOps = new SheetOps(id)


}

class SheetOps(sheetID: ValidID) {
  def submit(op: SheetOp.Seed): Res[SheetOp.Ref] = ???

  def query: Res[Seq[SheetOp.Ref]] = ???

  def get(id: SheetOpID): Res[Option[Seed]] = ???
}

