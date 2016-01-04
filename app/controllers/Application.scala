package controllers

import play.api._
import play.api.mvc._
// stuff related to the database
import models.Heroes
import slick.driver.PostgresDriver.api._
import slick.driver.PostgresDriver
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play

class Application extends Controller {

  val dbConfig = DatabaseConfigProvider.get[PostgresDriver](Play.current)
  import dbConfig.driver.api._
  val heroes = TableQuery[Heroes]
  val db = dbConfig.db

  def index = Action.async { 
    db.run(heroes.result).map(res => Ok(res.toString)) 
    // default route 

  }
}
