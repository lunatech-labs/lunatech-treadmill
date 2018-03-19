package edu.caeus.treadmill.tests


import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.{RouteTest, TestFrameworkInterface}
import akka.util.ByteString
import edu.caeus.treadmill.main.TreadmillModule
import utest._

object MainSpec extends TestSuite with RouteTest with TestFrameworkInterface {
  val tests = Tests {
    "Routes test" - {
      val module = new TreadmillModule()

      val content = HttpEntity.Strict(contentType = ContentTypes.`application/json`, ByteString("{}"))
      Put("/v1/sheets/maria", content) ~> module.httpRoutes ~> check {
        responseAs[String] ==> "{}"
      }
    }

  }

  override def failTest(msg: String): Nothing =
  //Knowing I had to do this implied a lot of reading regarding how scala testing works
    throw new java.lang.AssertionError(msg)

}
