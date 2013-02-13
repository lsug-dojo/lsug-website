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

  "Index page" should {
    "display the right title" in new WithApplication {
      val pastMeetings: Seq[Meeting] = Seq.empty
      val upcomingMeetings: Seq[Meeting] = Seq.empty
      val html = views.html.index(pastMeetings, upcomingMeetings)
      html.toString must contain("London")
    }
  }
}

// this test takes 14 s on my computer
/*
class HomePageSpec extends Specification {
  "HomePage" should {
    "have all the main menus" in {
      running(TestServer(3333), HTMLUNIT) {
        browser =>
          browser.goTo("http://localhost:3333/")
          browser.$("ul.nav a").first.getText must equalTo("About")
      }
    }
  }
}
*/
