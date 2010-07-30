import sbt._

class vBaDProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.1-SNAPSHOT"
  val commonsHttpVersion = "3.1"
  val jettyVersion = "6.1.22"
  val junitVersion = "4.5"
  val specsVersion = "1.6.5"
  val h2databaseVersion = "1.2.138"

  val scalatoolsSnapshot = "Scala Tools Snapshot" at "http://scala-tools.org/repo-snapshots/"

  // If you're using JRebel for Lift development, uncomment
  // this line
  // override def scanDirectories = Nil

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "commons-httpclient" % "commons-httpclient" % commonsHttpVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % jettyVersion % "test->default",
    "junit" % "junit" % junitVersion % "test->default",
    "org.scala-tools.testing" %% "specs" % specsVersion % "test->default",
    "com.h2database" % "h2" % h2databaseVersion
  ) ++ super.libraryDependencies
}
