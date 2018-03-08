package edu.caeus.treadmill.algebra


import upickle.Js
import upickle.default.{Reader, macroR, _}

case class Sheet(name: String, data: Js.Value) {

}

case class SheetRef()
object SheetRef {


  implicit def writer: Writer[SheetRef] = macroW
}

case class SheetOp()
object SheetOp {
  implicit def reader: Reader[SheetOp] = macroR
}


object Sheet {
  implicit def reader: Reader[Sheet] = macroR

  implicit def writer: Writer[Sheet] = macroW
}
