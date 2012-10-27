import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "lsug"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        "com.mongodb.casbah" %% "casbah" % "2.1.5-1"
      // Add your project dependencies here,
    )
    
    

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    	resolvers += "sonatype repository" at "https://oss.sonatype.org/content/groups/scala-tools/" 
     // Add your own project settings here      
    )

}
