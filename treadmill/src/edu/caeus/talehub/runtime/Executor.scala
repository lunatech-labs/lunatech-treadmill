package edu.caeus.talehub.runtime

import scala.language.higherKinds
import scala.util.control.NonFatal


trait Program

class Executor {

}

object Executor {
  def once(program: => Program) = {
    try {

    } catch {
      case NonFatal(e) =>

    }
  }


}

