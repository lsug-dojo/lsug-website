package services
import models.Meeting
import java.util.Date
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MockMeetingService extends MeetingService {

  override def past = Future {
    List(
      Meeting("past meeting 1", "past meeting description 1", new Date(), "www.meetup.com", "id2", "past"),
      Meeting("past meeting 2", "past meeting description 2", new Date(), "www.meetup.com", "id3", "past"),
      Meeting("past meeting 3", "past meeting description 3", new Date(), "www.meetup.com", "id4", "past"))
  }

  override def future = Future {
    List(
      Meeting("future meeting 1", "future meeting description 1", new Date(), "www.meetup.com", "id5", "future"),
      Meeting("future meeting 2", "future meeting description 2", new Date(), "www.meetup.com", "id6", "future"),
      Meeting("future meeting 3", "future meeting description 3", new Date(), "www.meetup.com", "id7", "future"))
  }
}