package net.usersource.vbad.lib

import xml.{XML, Elem, Node}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.DateTime
import collection.mutable.ListBuffer



case class CruiseJob( name: String, buildNumber: Int, status: String, timestamp: DateTime )

case class CruiseStage( name: String, buildNumber: Int, jobs: List[CruiseJob] ) {

  val status = {
    var s = "Success"
    jobs.foreach( job => job.status match {
      case "Success" => {}
      case "Failure" => s = "Failure"
      case _ => s = "NotBuilt"
    })
    s
  }

  val colour = { status match {
    case "Success" => "green"
    case "Failure" => "red"
    case "NotBuilt" => "yellow"
  }}

  def toXhtml: Elem = {
    <div name="stage-container" style="position: relative; left: 5px; top: 5px; width: 410px; height: 50px">
      <div name="stage" style={ "position: relative; left: 5px; top: 5px; width: 400px; height: 40px; background-color: " + colour + "; text-align: center; vertical-align: middle;"}>
        {name}
       </div>
     </div>
  }
}

case class CruisePipeline( name: String, stages: List[CruiseStage] ) extends BuildResults {
  val colour = {
    var c = "#006600" // dark grean
    stages.foreach( stage => stage.colour match {
      case "green" => {}
      case "red" => c = "#660000"
      case _ => if( c != "red" ) c = "#666666"
    })
    c
  }

  override def toXhtml: Elem = {
    val styleHeight = 5 + (stages.length + 1) * 50

    <div name="pipeline" style={ "width: 430px; height: " + styleHeight + "px; background-color: " + colour +"; font-size: 30px;" }>
      <div name="pipeline-title-container" style="position: relative; left: 5px; top: 5px; width: 410px; height: 50px">
        <div name="pipeline-container" style="position: relative; left: 5px; top: 5px; width: 400px; height: 40px; text-align: center; vertical-align: middle;">
          {name}
         </div>
      </div>
      {for( stage <- stages ) yield stage.toXhtml}
    </div>
  }
}


object CruisePipeline {
  private val dtParser: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")

  private def rawStagesFromXml(name: String, xml: Elem): List[CruiseStage] = {
    var stages = new ListBuffer[CruiseStage]()
    xml \\ "Project" foreach( project => {
      val h = project.attribute("name").get.text.split(" :: ").toList
      val buildLabel = project.attribute("lastBuildLabel").get.text.split(" :: ").toList
      val buildNumber = buildLabel(0).toInt
      if( h(0) == name ) h match {
        case List(pipelineName, stage: String) => {
          if( ! stages.exists(st => st.name == stage) ) stages += new CruiseStage(stage,buildNumber,Nil)
        }
        case List(pipelineName, stageName: String, jobName: String ) => {
          val status = project.attribute("lastBuildStatus").get.text
          val timestamp = dtParser.parseDateTime(project.attribute("lastBuildTime").get.text)
          val job = new CruiseJob(jobName,buildNumber,status,timestamp)
          if( ! stages.exists(stage => stage.name == stageName) ) stages += new CruiseStage(stageName,buildNumber,List(job))
          else {
            val idx = stages.findIndexOf(stage => stage.name == stageName)
            val stage = stages(idx)
            stages.remove(idx)
            stages += new CruiseStage( stage.name, buildNumber, job :: stage.jobs )
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
    val stages = rawStagesFromXml(name,xml)
    if( stages.isEmpty ) None
    else {
      if( stages.forall( stage => stage.buildNumber == stages(0).buildNumber ) ) {
        Some(new CruisePipeline(name,stages))
      }
      else {
        var s = new ListBuffer[CruiseStage]()
        stages.foreach( stage => {
          if( stage.buildNumber == stages(0).buildNumber ) s += stage
          else {
            var j = new ListBuffer[CruiseJob]()
            stage.jobs.foreach( job => j += new CruiseJob(job.name, job.buildNumber, "NotBuilt", job.timestamp) )
            s += new CruiseStage(stage.name,stage.buildNumber,j.toList)
          }
        } )
        Some(new CruisePipeline(name,s.toList))
      }
    }
  }

}



