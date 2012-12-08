import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "lsug"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.mongodb" % "casbah_2.9.0" % "2.4.1",
      "se.radley" %% "play-plugins-salat" % "1.1",
      "com.novus" %% "salat" % "1.9.1"
      )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "stylesheets" ** "main.less"),
    	resolvers += "sonatype repository" at "https://oss.sonatype.org/content/groups/scala-tools/",
    	routesImport += "se.radley.plugin.salat.Binders._",
        templatesImport += "org.bson.types.ObjectId"
      )

}
