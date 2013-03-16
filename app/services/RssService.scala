package services

import models.Item
import scala.xml._
import play.api.libs.ws._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait RssService {
  def nextItems(url: String): Future[Seq[Item]]
}

object RssService extends RssService{

  override def nextItems(url: String): Future[Seq[Item]] = {

    WS.url(url)
      .withHeaders("user-agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22")
      .get().map { root =>
        (root.xml \\ "item").map(
          node => buildItem(node)
        ).filterNot( item => item.title.startsWith("Re:"))
    }
  }

  def buildItem(node: Node): Item = {
    Item(
      (node \\ "title").text,
      (node \\ "description").text,
      (node \\ "link").text,
      (node \\ "pubDate").text
    )
  }
}


