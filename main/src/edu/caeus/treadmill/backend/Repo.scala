package edu.caeus.treadmill.backend

import java.net.URI
import java.nio.file.Path

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import edu.caeus.treadmill.backend.Repo.CommitID
import edu.caeus.treadmill.backend.internal.GitRepo

import scala.concurrent.Future


trait Repo {

  def update(file: String, content: String): Future[CommitID]

  def touch(file: String): Future[Boolean]

  def show(file: String, version: Option[CommitID]): Future[Option[String]]

}

object Repo {
  type CommitID = String

  def git(rootPath: Uri.Path)(actorSystem: ActorSystem): Repo = new GitRepo(rootPath)(actorSystem)
}
