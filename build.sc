// build.sc
import mill._
import mill.scalalib._

def hey:String = "Hola"

object main extends ScalaModule {
  override def scalaVersion = "2.12.4"


  //override def scalacOptions: Target[Seq[String]] = super.scalacOptions() :+ "-Ypartial-unification"


  override def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:0.5.1",
    ivy"com.typesafe.akka::akka-http:10.0.11",
    ivy"org.eclipse.jgit:org.eclipse.jgit:4.10.0.201712302008-r",
    ivy"org.typelevel::cats-core:1.0.1",
    ivy"org.typelevel::cats-free:1.0.1"
  )

  object test extends Tests {
    override def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.0",
      ivy"com.typesafe.akka::akka-http-testkit:10.0.11")

    def testFrameworks = Seq("utest.runner.Framework")
  }

}