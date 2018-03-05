package edu.caeus.treadmill.algebra


import upickle.Js
import upickle.default.{Reader, macroR, _}

case class Sheet(name: String, data: Js.Value) {

}

object Sheet {
  implicit def reader: Reader[Sheet] = macroR

  implicit def writer: Writer[Sheet] = macroW
}
