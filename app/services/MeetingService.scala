package services
import models.{ Meeting, MeetupImporter }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait MeetingService {
  def past: Future[Seq[Meeting]]
  def future: Future[Seq[Meeting]]
}

object ConcreteMeetingService extends MeetingService {
  override def past = MeetupImporter.getMeetings("past")
  override def future = MeetupImporter.getMeetings("upcoming")
}
