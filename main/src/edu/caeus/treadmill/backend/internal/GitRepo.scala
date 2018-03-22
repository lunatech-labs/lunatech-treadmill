package edu.caeus.treadmill.backend.internal

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.Uri
import edu.caeus.treadmill.backend.Repo
import edu.caeus.treadmill.backend.Repo.CommitID

import scala.concurrent.Future

private[backend] class GitRepo(rootPath: Uri.Path)(actorSystem: ActorSystem) extends Repo {

  private val transactor: ActorRef = actorSystem.actorOf(GitRepoTransactor.props, "default_gitrepo_transactor")

  override def update(file: String, content: String): Future[CommitID] = ???

  override def touch(file: String): Future[Boolean] = ???

  override def show(file: String, version: Option[CommitID]): Future[Option[String]] = ???
}

