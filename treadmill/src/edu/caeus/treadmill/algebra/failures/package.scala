package edu.caeus.treadmill.algebra

package object failures {

  sealed trait Managed {
    this: Throwable =>
  }

  case class ResourceNotFoundException() extends Exception() with Managed
  case class UnauthenticatedException() extends Exception() with Managed
  case class BadRequestException(cause:Option[Throwable]=None) extends Exception(null,cause.orNull) with Managed
}
