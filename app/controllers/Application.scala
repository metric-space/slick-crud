package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
// stuff related to the database
import models.Heroes
import slick.driver.PostgresDriver.api._
import slick.driver.PostgresDriver
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play
import play.api.Logger


class Application extends Controller {

  val dbConfig = DatabaseConfigProvider.get[PostgresDriver](Play.current)
  import dbConfig.driver.api._
  val heroes = TableQuery[Heroes]
  val db = dbConfig.db

  def index = Action.async { 
    db.run(heroes.result)
      .map(res =>{ 
          val egg = res.foldLeft(Json.arr()) ((r,c) => r :+ Json.obj("rank" -> c._1, "name" -> c._2))  
          Ok(Json.obj("result"-> egg))
      }
    )
  }

  def create = Action.async { implicit request =>
  
    Logger.debug(request.body.asJson.toString)

    val dataWrapped:Option[(String, String)] = for {
      jsonBody <- request.body.asJson
      classRank <- (jsonBody \ "classRank").asOpt[String]
      name <- (jsonBody \ "name").asOpt[String]
    } yield (classRank,name)


    dataWrapped match {
      case None => scala.concurrent.Future { Ok("Nope") }
      case Some(data) => db.run(heroes+=data)
                                .map(head => Ok(head.toString))
    }

  }


  def delete(classRank: String) = Action.async { implicit request =>
      db.run(heroes.filter(_.classRank === classRank).delete)
                             .map(head => Ok(head.toString))
  }

  def update = Action.async { implicit request =>
     val dataWrapped:Option[(String, String)] = for {
      jsonBody <- request.body.asJson
      classRank <- (jsonBody \ "classRank").asOpt[String]
      name <- (jsonBody \ "name").asOpt[String]
    } yield (classRank,name)

    dataWrapped match {
      case None => scala.concurrent.Future { Ok("Nope") }
      case Some(data) => db.run(heroes.filter(_.classRank === data._1).result.map(res => res.name.update("luke").updateStatement))
                                .map(head => Ok(head.toString))
    }
  
  }
}
