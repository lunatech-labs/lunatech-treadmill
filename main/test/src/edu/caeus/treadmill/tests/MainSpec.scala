package edu.caeus.treadmill.tests


import java.nio.file.{Files, Paths}

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model.Uri.Path.{Empty, Segment, Slash}
import akka.http.scaladsl.testkit.{RouteTest, TestFrameworkInterface}
import edu.caeus.treadmill.backend.data.{Sheet, ValidID}
import edu.caeus.treadmill.backend.{Repo, SheetsRegistry}
import upickle.Js
import utest._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object MainSpec extends TestSuite with RouteTest with TestFrameworkInterface {

  implicit class FutureOps[T](private val value: Future[T]) {
    def await: T = Await.result(value, 10.seconds)
  }

  val tests = Tests {
    "Routes test" - (if (false) {
      val sheetsRegistry = new SheetsRegistry
      sheetsRegistry.set(Sheet(id = ValidID("ezra"),
        data = Js.Null)).run(ValidID("caeus"))
        .await ==> Sheet.Ref(ValidID("ezra"))


    })

    "GitRepo works" - {
      implicit val actorSystem = ActorSystem("test")
      val path = Paths.get("out", "whateverrepo")

      Files.createDirectories(path)

      val path2 = Path("out/whateverrepo")

      val repo = Repo.git(path2.asInstanceOf[Segment])(actorSystem)

      println(path2.getClass)


    }

  }

  override def failTest(msg: String): Nothing =
  //Knowing I had to do this implied a lot of reading regarding how scala testing works
    throw new java.lang.AssertionError(msg)

}
