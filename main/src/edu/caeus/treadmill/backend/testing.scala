package edu.caeus.treadmill.backend


import scala.concurrent.Future
import scala.language.higherKinds


object shapeless {
  asd =>

  sealed trait HList

  sealed trait HNil extends HList

  case object HNil extends HNil {
    def ::[H](h: H) = asd.::(h, this)
  }

  case class ::[H, T <: HList](head: H, tail: T) extends HList

}

object testing {

  import edu.caeus.treadmill.backend.shapeless._

  case class E[F[_], EHL <: HList, IHL <: HList]() {
    type Out = IHL
  }

  implicit def evHList[F[_]]: E[F, HNil, HNil] = ???

  implicit def evCons[F[_], H, EHT <: HList, IHT <: HList](implicit ev: E[F, EHT, IHT]): E[F, F[H] :: EHT, H :: IHT] = ???


  def asd[F[_], HL <: HList, IHL <: HList](hlist: HL)(implicit ev: E[F, HL, IHL]): IHL = {
    println(ev)
    ???
  }


  val s = asd(Future.successful("asd") :: HNil)

}

object whatever {

}