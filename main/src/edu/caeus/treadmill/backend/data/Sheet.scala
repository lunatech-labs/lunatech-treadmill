package edu.caeus.treadmill.backend.data

import upickle.Js

case class Sheet(id: ValidID,
                 data: Js.Value)

object Sheet {

  case class Ref(id: ValidID)

}


