package net.usersource.vbad.lib

import xml.{XML, Elem, Node}

class HudsonBuildStatus( name:String, status: String, timestamp: String )
        extends BuildStatus(name,status,timestamp)

object HudsonBuildFactory {

  private def listFromXml(xmlAsString : String ): List[HudsonBuildStatus] = {
    listFromXml(XML.loadString(xmlAsString))
  }

  private def listFromXml(xml: Elem): List[HudsonBuildStatus] = {
    var listOfProjects: List[HudsonBuildStatus] = Nil
    xml \\ "entry" foreach( project => listOfProjects = fromXml(project) :: listOfProjects )
    listOfProjects
  }

  private def getBuildName( title: String ): String = {
    title.substring(0,title.findIndexOf( c => c == '#')-1)
  }

  private def getBuildStatus( title: String ): String = {
    title.substring(title.findIndexOf(c => c == '#')+1)
  }

  private def fromXml(xmlNode:Node):HudsonBuildStatus = {
    new HudsonBuildStatus( getBuildName((xmlNode \ "title").text),
                           getBuildStatus((xmlNode \ "title").text),
                           (xmlNode \ "published").text )
  }

  def build( name:String, source: String ): HudsonBuildStatus = {
    listFromXml(source).filter( status => status.name.startsWith( name + " #") ).head
  }


}