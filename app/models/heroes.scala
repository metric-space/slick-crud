package models

import slick.driver.PostgresDriver.api._

class Heroes(tag: Tag) extends Table[(String, String)](tag,"hero_registry"){
  def classRank = column[String]("class_rank", O.PrimaryKey)
  def name = column[String]("name")

  def * = (classRank, name) 
}

