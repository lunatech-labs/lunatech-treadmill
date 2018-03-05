// build.sc
import mill._
import mill.define.Target
import mill.scalalib._


object treadmill extends ScalaModule {
  override def scalaVersion = "2.12.4"


  //override def scalacOptions: Target[Seq[String]] = super.scalacOptions() :+ "-Ylog-classpath"



  override def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:0.5.1",
    ivy"com.typesafe.akka::akka-http:10.0.11"
  )

  object test extends Tests {
    override def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.0",
      ivy"com.typesafe.akka::akka-http-testkit:10.0.11")

    def testFrameworks = Seq("utest.runner.Framework")
  }
}