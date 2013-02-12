package controllers

import play.api.mvc._
import services.{ MeetingService, MockMeetingService, ConcreteMeetingService }
import models._
import java.util.Date
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.{ Await, Future }
import concurrent.duration.Duration
import models.MeetupImporter.getMeetings
import play.api.cache.Cached
import play.api.Play
import play.api.Play.current
import java.text.SimpleDateFormat

// Neo4j
// http://b51498187:fd6b46e88@5701820d8.hosted.neo4j.org:7976

// MongoDB
// mongodb://heroku_app8030104:u37aro46ggr991mvgdmb5on5n9@ds037987.mongolab.com:37987/heroku_app8030104

class Application(meetingService: MeetingService) extends Controller {

  val timeout = 20.seconds
  val configuration = Play.current.configuration
  def meetupId = configuration.getString("meetup.meetupId")
  val cacheSeconds = configuration.getInt("cache.seconds").get

  def index = Cached("index", cacheSeconds) {
    Action {
      Ok(views.html.index())
    }
  }

  def group = Action {
    val groupname = "scala-london"
    Ok(views.html.iframewrapper(groupname))
  }

  def jobs = Action {
    Ok("Jobs")
  }

  def pastTalks = Await.result(meetingService.past, timeout)

  def upcomingMeetings = Await.result(meetingService.future, timeout) reverse

  val dateFormat = new SimpleDateFormat("yyyy MMM")
  def formatDate(d: Date): String = dateFormat.format(d)

  def monthName(n: Int): String = {
    val months = new java.text.DateFormatSymbols().getShortMonths
    months(n)
  }
}

object Application extends Application(ConcreteMeetingService) {

}
