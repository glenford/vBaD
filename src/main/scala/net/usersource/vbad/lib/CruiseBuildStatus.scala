package net.usersource.vbad.lib

import xml.{XML, Elem, Node}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.DateTime
import collection.mutable.ListBuffer

trait BuildResults {
  def toXhtml = <div>UNDEFINED</div>
}

case class CruiseJob( name: String, status: String, timestamp: DateTime )

case class CruiseStage( name: String, jobs: List[CruiseJob] ) {

  var colour = "green"
  jobs.foreach( job => job.status match {
    case "Success" => {}
    case "Failure" => colour = "red"
    case _ => colour = "yellow"
  })

  def toXhtml: Elem = {
    <div name="stage-container" style="position: relative; left: 5px; top: 5px; width: 110px; height: 30px">
      <div name="stage" style="position: relative; left: 5px; top: 5px; width: 100px; height: 20px; background-color: {colour}; text-align: center; vertical-align: middle;">
        {name}
       </div>
     </div>
  }
}

case class CruisePipeline( name: String, stages: List[CruiseStage] ) extends BuildResults {
  override def toXhtml: Elem = {
    <div name="pipeline" style="width: 130px; height: 130px; background-color: #660000;">
      <div name="pipeline-title-container" style="position: relative; left: 5px; top: 5px; width: 110px; height: 30px">
        <div name="pipeline-container" style="position: relative; left: 5px; top: 5px; width: 100px; height: 20px; text-align: center; vertical-align: middle;">
          {name}
         </div>
      </div>
      {stages.foreach(_.toXhtml)}
    </div>
  }
}


object CruisePipeline {
  private val dtParser: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")

  private def stagesFromXml(name: String, xml: Elem): List[CruiseStage] = {
    var stages = new ListBuffer[CruiseStage]()
    xml \\ "Project" foreach( project => {
      val h = project.attribute("name").get.text.split(" :: ").toList
      if( h(0) == name ) h match {
        case List(pipelineName, stage: String) => {
          if( ! stages.exists(st => st.name == stage) ) stages += new CruiseStage(stage,Nil)
        }
        case List(pipelineName, stageName: String, jobName: String ) => {
          val status = project.attribute("lastBuildStatus").get.text
          val timestamp = dtParser.parseDateTime(project.attribute("lastBuildTime").get.text)
          val job = new CruiseJob(jobName,status,timestamp)
          if( ! stages.exists(stage => stage.name == stageName) ) stages += new CruiseStage(stageName,List(job))
          else {
            val idx = stages.findIndexOf(stage => stage.name == stageName)
            val stage = stages(idx)
            stages.remove(idx)
            stages += new CruiseStage( stage.name, job :: stage.jobs )
          }
        }
        case _ => {}
      }
    } )
    stages.toList
  }

  def apply( name: String, xmlAsString: String ): Option[CruisePipeline] = {
    apply(name,XML.loadString(xmlAsString))
  }

  def apply( name: String, xml: Elem ): Option[CruisePipeline] = {
    val stages = stagesFromXml(name,xml)
    if( stages.isEmpty ) None else Some(new CruisePipeline(name,stages))
  }

}

/*

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

*/