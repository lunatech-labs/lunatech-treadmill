package edu.caeus.treadmill.tests


import akka.http.scaladsl.testkit.{RouteTest, TestFrameworkInterface}
import edu.caeus.treadmill.algebra.Sheet
import edu.caeus.treadmill.main.TreadmillModule
import upickle.Js
import utest._

object MainSpec extends TestSuite with RouteTest with TestFrameworkInterface {
  val tests = Tests {
    "Routes test" - {
      val module = new TreadmillModule()

      Get("/v1") ~> module.httpRoutes ~> check{
        responseAs[String] ==> "Hola!"
      }
    }
    "Upickle unmarsharller" - {
      import upickle.default._
      write(Sheet("Hola",Js.Null))
      1
    }
    'test3 - {

    }
  }

  override def failTest(msg: String): Nothing =
  //Knowing I had to do this implied a lot of reading regarding how scala testing works
    throw new java.lang.AssertionError(msg)

}
