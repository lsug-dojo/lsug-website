package services
import models.{Venue, Meeting}
import java.util.Date
import models.{ Meeting, MeetupImporter }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait MeetingService {
  def past: Future[Seq[Meeting]]
  def upcoming: Future[Seq[Meeting]]
}

object MeetingService extends MeetingService {
  override def past = MeetupImporter.getMeetings("past")
  override def upcoming = MeetupImporter.getMeetings("upcoming")
}

object MockMeetingService extends MeetingService {

  override def past = Future {
    List(
      Meeting("past meeting 1", "past meeting description 1", new Date(), "www.meetup.com", "id2", "past", Venue()),
      Meeting("past meeting 2", "past meeting description 2", new Date(), "www.meetup.com", "id3", "past", Venue()),
      Meeting("past meeting 3", "past meeting description 3", new Date(), "www.meetup.com", "id4", "past", Venue()))
  }

  override def upcoming = Future {
    List(
      Meeting("future meeting 1", "future meeting description 1", new Date(), "www.meetup.com", "id5", "future", Venue()),
      Meeting("future meeting 2", "future meeting description 2", new Date(), "www.meetup.com", "id6", "future", Venue()),
      Meeting("future meeting 3", "future meeting description 3", new Date(), "www.meetup.com", "id7", "future", Venue()))
  }
}
