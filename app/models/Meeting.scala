package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._

// TODO It is not immutable because Date is not immutable
case class Meeting (
    name: String, 
    description: String, 
    time: Date, 
    event_url: String,
    event_id: String,
    status: String,
    venue: Venue,
    timestamp: Date = new Date
    )
case class Venue(name:String="", address:String="", city:String="", country:String="", lat:String="0", lon:String="0")

object Meeting extends ModelCompanion[Meeting, ObjectId] {

  val dao = new SalatDAO[Meeting, ObjectId](collection = mongoCollection("meetings2")) {}
  
  //dao.collection.dropCollection
  //dao.collection.dropIndexes()
  //dao.collection.ensureIndex(DBObject("timestamp" -> 1, "expireAfterSeconds" -> 90))
  
  def findOneByName(name: String): Option[Meeting] = dao.findOne(MongoDBObject("name" -> name))
  
  val dummy = Meeting("No meeting scheduled", "", new Date, "http://lsug.org", "", "future", Venue())
  
}

