import sbt._
import webbytest.HtmlTestsProject

class vBaDProject(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with ProguardProject with HtmlTestsProject {
  val liftVersion = "2.1"
  val commonsHttpVersion = "3.1"
  val jettyVersion = "6.1.22"
  val junitVersion = "4.5"
  val specsVersion = "1.6.5"
  val h2databaseVersion = "1.2.138"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "commons-httpclient" % "commons-httpclient" % commonsHttpVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % jettyVersion % "compile->default",
    "junit" % "junit" % junitVersion % "test->default",
    //"org.scala-tools.testing" %% "specs" % specsVersion % "test->default",
    "org.scalatest" % "scalatest" % "1.2" % "test->default",
    "com.h2database" % "h2" % h2databaseVersion
  ) ++ super.libraryDependencies

  override def proguardInJars = super.proguardInJars +++ scalaLibraryPath

  override def proguardOptions = List(
      proguardKeepAllScala,
      "-dontshrink",
      "-dontoptimize",
      "-dontobfuscate"
  )
}
