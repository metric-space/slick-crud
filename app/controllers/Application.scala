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

  def index = Action {
    // default route 

  }

  def create = Action.async { implicit request =>
    //create route
    Ok("Route to List")
  }

  def read  = Action.async { implicit request =>
    //read route
    Ok("Route to List")
  }

  def update  = Action.async { implicit request =>
    //update route
    Ok("Route to List")
  }

  def delete = Action.async { implicit request =>
    //delete route
    Ok("...")
  }

}
