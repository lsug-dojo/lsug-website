import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "lsug"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.lambdaworks" %% "jacks" % "2.1.2",
    "org.mongodb" % "casbah_2.10" % "2.5.0",
    "com.novus" %% "salat-core" % "1.9.2-SNAPSHOT",
    "com.novus" %% "salat-util" % "1.9.2-SNAPSHOT",
    "se.radley" %% "play-plugins-salat" % "1.2-SNAPSHOT",
    "org.neo4j" % "neo4j-remote-graphdb" % "0.9-1.3.M01")

  val main = play.Project(appName, appVersion, appDependencies).settings(
    lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "stylesheets" ** "main.less"),
    resolvers ++= Seq(
      "sonatype repository" at "http://oss.sonatype.org/content/repositories/snapshots",
      "typesafe artefactory" at "http://typesafe.artifactoryonline.com/typesafe/repo",
      "jgit-repository" at "http://download.eclipse.org/jgit/maven"))
}
