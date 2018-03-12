package edu.caeus.treadmill.algebra


import upickle.Js
import upickle.default.{Reader, macroR, _}

case class Sheet(id: String, data: Js.Value)

object Sheet {

  case class Seed(data: Js.Value)

  object Seed {
    implicit def reader: Reader[Seed] = macroR
  }

  case class Ref(id: String)

  object Ref {
    implicit def writer: Writer[Ref] = macroW
  }

  implicit def writer: Writer[Sheet] = macroW


}




case class SheetOp(id: String, data: String, diff: String)

object SheetOp {

  case class Seed(data: Js.Value)

  object Seed {
    implicit def reader: Reader[Seed] = macroR
  }

  case class Ref(id: String)

  object Ref {
    implicit def writer: Writer[Ref] = macroW
  }

  implicit def writer: Writer[SheetOp] = macroW
}


