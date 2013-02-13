import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import java.util.Date
import controllers.Application
import services.MockMeetingService
import scala.concurrent.Await
import models.Meeting

object MockApplication extends Application {
  override val meetingService = MockMeetingService
}

class ApplicationSpec extends Specification {

  "Application" should {

    "format Date with year and month" in new WithApplication {
      val application = MockApplication
      val d = new Date(0)
      application.formatDate(d) must beEqualTo("1970 Jan")
    }
  }
}

class TemplateSpec extends Specification {

  "The index page" should {

    "display the right title" in new WithApplication {
      val pastMeetings: Seq[Meeting] = Seq.empty
      val upcomingMeetings: Seq[Meeting] = Seq.empty

      val html = views.html.index(pastMeetings, upcomingMeetings)
      html.toString must contain("London")
    }
  }
}
