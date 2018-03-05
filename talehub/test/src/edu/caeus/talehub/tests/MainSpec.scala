package edu.caeus.talehub.tests


import akka.http.scaladsl.testkit.{RouteTest, ScalatestRouteTest, TestFrameworkInterface}
import edu.caeus.talehub.main.TalehubModule
import org.scalatest.Suite

object MainSpec extends RouteTest with TestFrameworkInterface {

  override def failTest(msg: String): Nothing = ???
}