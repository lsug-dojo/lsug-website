 package functional

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._

/**
 *
 * @author fanta
 */
class HomePageSpec extends Specification {

  "HomePage" should {
    "have all the main menus" in {
      running(TestServer(3333), HTMLUNIT) {
        browser =>
          browser.goTo("http://localhost:3333/")
          browser.$("ul.nav a").first.getText must equalTo("About")
          //browser.$("ul.nav a[href]").first.getText must equalTo("About")
      }
    }
  }
}
