package models


import play.api.libs.ws._
import play.api.libs.json.JsValue
import java.util.Date
import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.Future

object MeetupImporter {
  
 // julien's key (need to create one for the app)
  val key = "24a4b6977821146284c192b5d30"
  val groupId = "1562942"
    

  def pastMeetings = fetchMeetings("past")

    
  def fetchMeetings(status: String): Future[Seq[Meeting]] = {
    def buildEventsRequest: String = s"https://api.meetup.com/2/events?key=$key&group_id=$groupId&page=200&status=$status"


    val meetings = Meeting.findAll().filter( _.status == status ).toList.reverse

    if (meetings.size > 0) {
      println("returing cached values")
      return Future { meetings }
    }
    println("cache miss")

    def getAllMeetingsResponse = {
      val upcomingURL = buildEventsRequest
      println("Meetup Upcoming: " + upcomingURL)
      WS.url( upcomingURL ).get()
    }

    getAllMeetingsResponse.map( response => {
      val meetings = (response.json \  "results").asOpt[Seq[JsValue]]

      meetings match {
        case Some(seq) => seq.reverse.map(parseJsonMeeting(_))
        case _ => Nil
      }
    })
  }

  def upcomingMeetings: Future[Seq[Meeting]] = fetchMeetings("upcoming")

  def parseJsonMeeting(value : JsValue): Meeting = {
    val name = (value \ "name").asOpt[String]
    val description = (value \ "description").asOpt[String]
    val eventUrl = (value \ "event_url").asOpt[String]
    val date = (value \ "time").asOpt[Long]
    val id = (value \ "id").asOpt[String]
    val status = (value \ "status").asOpt[String]

    (name, description, date, eventUrl, id, status) match {

      case (Some(n), Some(desc), Some(timestamp), Some(url), Some(id), Some(st)) =>  
          val m = Meeting(n, desc, new Date(timestamp), url, id, st)
          Meeting.dao.insert(m)
          println(s"inserted $status $name")
          m
        case _ => throw new IllegalStateException("Invalid meeting")
    }

  }

}