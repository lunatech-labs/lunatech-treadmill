package edu.caeus.treadmill.logic

import cats.data.Kleisli
import edu.caeus.treadmill.algebra.{Sheet, SheetOp, SheetRef, User}
import upickle.Js

import scala.concurrent.Future

class SheetEngine {

  type Res[T] = Kleisli[Future, User, T]

  def create(body: Js.Value): Res[SheetRef] = {
    ???
  }

  def query: Res[Seq[SheetRef]] = {
    ???
  }

  def byId(id: String): Res[Option[Sheet]] = ???

  def update(id: String, op: SheetOp): Res[SheetRef] = ???

}
