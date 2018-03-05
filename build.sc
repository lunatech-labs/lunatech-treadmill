// build.sc
import mill._
import mill.define.Target
import mill.scalalib._


object talehub extends ScalaModule {
  override def scalaVersion = "2.12.4"


  //override def scalacOptions: Target[Seq[String]] = super.scalacOptions() :+ "-Ylog-classpath"



  override def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:0.5.1",
    ivy"com.typesafe.akka::akka-http:10.0.11"
  )
  object test extends Tests {
    override def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.0",
      ivy"org.scalatest::scalatest:3.0.5",
      ivy"com.typesafe.akka::akka-http-testkit:10.0.11")

    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}