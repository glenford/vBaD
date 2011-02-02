import sbt._
import net.usersource.jettyembed.JettyEmbedWebProject

class vBaDProject(info: ProjectInfo) extends JettyEmbedWebProject(info) with IdeaProject {
  val liftVersion = "2.2"
  val commonsHttpVersion = "3.1"
  val junitVersion = "4.5"
  val specsVersion = "1.6.5"
  val h2databaseVersion = "1.2.138"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "commons-httpclient" % "commons-httpclient" % commonsHttpVersion % "compile->default",
    "junit" % "junit" % junitVersion % "test->default",
    "org.scalatest" % "scalatest" % "1.2" % "test->default",
    "com.h2database" % "h2" % h2databaseVersion
  ) ++ super.libraryDependencies

}

