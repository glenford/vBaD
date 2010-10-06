package net.usersource.vbad.lib

import xml.{XML, Elem, Node}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.DateTime


case class CruiseJob( name: String, status: String, timestamp: DateTime )
case class CruiseStage( name: String, jobs: List[CruiseJob] )
case class CruisePipeline( name: String, stages: List[CruiseStage] )



object CruisePipeline {

  def apply( name: String, xmlAsString: String ): Option[CruisePipeline] = {
    None
  }

}



class CruiseBuildStatus( name:String, status: String, timestamp: String )
        extends BuildStatus(name,status,timestamp)

object CruiseBuildFactory {
  private val dtParser: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")

  private def listFromXml(xmlAsString : String ): List[CruiseBuildStatus] = {
    listFromXml(XML.loadString(xmlAsString))
  }

  private def listFromXml(xml: Elem): List[CruiseBuildStatus] = {
    var listOfProjects: List[CruiseBuildStatus] = Nil
    xml \\ "Project" foreach( project => listOfProjects = fromXml(project) :: listOfProjects )
    listOfProjects
  }

  private def fromXml(xmlNode:Node):CruiseBuildStatus = {
    new CruiseBuildStatus( (xmlNode \ "@name").text,
                           (xmlNode \ "@lastBuildStatus").text,
                           (xmlNode \ "@lastBuildTime").text)
  }

  def build( name:String, source: String ): Option[CruiseBuildStatus] = {
    listFromXml(source).sort( (a,b) => dtParser.parseDateTime(a.timestamp).isBefore(dtParser.parseDateTime(b.timestamp))).headOption
  }

  def buildPipeline(name:String, source: String ): List[CruiseBuildStatus] = {
    listFromXml(source).filter( status => status.name.startsWith(name) )
  }
}