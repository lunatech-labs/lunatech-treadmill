package edu.caeus.treadmill.algebra

import upickle.default

case class Credentials() {

}

object Credentials{
  implicit def reader: default.Reader[Nothing] = upickle.default.macroR
}