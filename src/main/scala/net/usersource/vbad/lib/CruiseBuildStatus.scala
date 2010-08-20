package net.usersource.vbad.lib

import xml.{XML, Elem, Node}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

class CruiseBuildStatus( name:String, status: String, timestamp: String )
        extends BuildStatus(name,status,timestamp)

object CruiseBuildFactory {
  private val dtParser: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-ddTHH:mm:ss")

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

}