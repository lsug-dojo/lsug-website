package models

import play.api.libs.ws._
import play.api.libs.json.JsValue
import play.api.Play
import java.util.Date
import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.Future
import play.api.cache.Cache
import Play.current

object MeetupImporter {

  val configuration = Play.current.configuration
  val key = configuration.getString("meetup.key")
  val groupId = configuration.getString("meetup.groupId")

  def pastMeetings: Future[Seq[Meeting]] = getMeetings("past")
  
  def upcomingMeetings: Future[Seq[Meeting]] = getMeetings("upcoming")

  def isDojo(meeting: Meeting): Boolean = {
    meeting.name.contains("ojo")
  }
  
  def getMeetings(status: String): Future[Seq[Meeting]] = {
    val eventsUrl = s"https://api.meetup.com/2/events?key=$key&group_id=$groupId&page=200&status=$status"

    def getAllMeetingsResponse = {
      val upcomingURL = eventsUrl
      println("Meetup Upcoming: " + upcomingURL)
      WS.url(upcomingURL).get()
    }

    getAllMeetingsResponse.map(response => {
      val meetings = (response.json \ "results").asOpt[Seq[JsValue]]

      meetings match {
        case Some(seq) => seq.reverse.map(parseJsonMeeting(_))
        case _ => Nil
      }
    })
  }

  def parseJsonMeeting(value: JsValue): Meeting = {
    val name = (value \ "name").asOpt[String]
    val description = (value \ "description").asOpt[String]
    val eventUrl = (value \ "event_url").asOpt[String]
    val date = (value \ "time").asOpt[Long]
    val id = (value \ "id").asOpt[String]
    val status = (value \ "status").asOpt[String]

    (name, description, date, eventUrl, id, status) match {

      case (Some(n), Some(desc), Some(timestamp), Some(url), Some(id), Some(st)) =>
        Meeting(n, desc, new Date(timestamp), url, id, st)

      case _ => throw new IllegalStateException("Invalid meeting")
    }

  }
}