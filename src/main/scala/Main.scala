package com.nashtech.slickdemo

import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main {

  val ghostRider = Movie(1, "Ghost Rider", LocalDate.of(2007, 12, 23), 123)
  val ironMan = Movie(2, "Iron Man", LocalDate.of(2003, 8, 12), 120)
  val amazingSpiderMan = Movie(3, "Amazing Spider Man", LocalDate.of(2012, 3, 15), 129)

  val dataToInsert = Seq(
    ghostRider,
    ironMan,
    amazingSpiderMan
  )

  def demoInsertMovie(): Unit = {
    val queryDescription = SlickTables.movieTable ++= dataToInsert
    val futureId: Future[Option[Int]] = Connection.db.run(queryDescription)

    futureId.onComplete {
      case Success(newMovieID) => println(s"Query was successful, new id is $newMovieID")
      case Failure(exception) => println(s"Query failed, reason: $exception")
    }
  }

  def demoReadAllMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.result)

    resultFuture.onComplete {
      case Success(moviesResult) => println(s"Fetched: ${moviesResult.mkString(",")}")
      case Failure(exception) => println(s"Query failed: $exception")
    }
  }

  def demoSomeMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.filter(_.name.like("%Ghost%")).result)

    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(exception) => println(s"Failed: $exception")
    }
  }

  def demoUpdate(): Unit = {
    val queryDescription = SlickTables.movieTable.filter(_.id === 3L).update(amazingSpiderMan.copy(lengthInMin = 124))
    val futureId: Future[Int] = Connection.db.run(queryDescription)

    futureId.onComplete {
      case Success(futureResult) => println(s"Updated: $futureResult rows")
      case Failure(exception) => println(s"Error with updating: $exception")
    }
  }

  def demoDelete(): Unit = {
    val resultFuture: Future[Int] = Connection.db.run(SlickTables.movieTable.filter(_.name.like("%Ghost%")).delete)

    resultFuture.onComplete {
      case Success(futureResult) => println(s"Deleted: $futureResult rows")
      case Failure(exception) => println(s"Error with deleting: $exception")
    }

  }

  def main(args: Array[String]): Unit = {
    //demoInsertMovie()
    //demoReadAllMovies()
    //demoSomeMovies()
    //demoUpdate()
    demoDelete()
    Thread.sleep(1000)
  }
}