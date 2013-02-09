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
    timestamp: Date = new Date
    )

object Meeting extends ModelCompanion[Meeting, ObjectId] {

  val dao = new SalatDAO[Meeting, ObjectId](collection = mongoCollection("meetings2")) {}
  
  dao.collection.dropIndexes()
  dao.collection.ensureIndex(DBObject("timestamp" -> 1, "expireAfterSeconds" -> 90))
  
  
  def findOneByName(name: String): Option[Meeting] = dao.findOne(MongoDBObject("name" -> name))
}

