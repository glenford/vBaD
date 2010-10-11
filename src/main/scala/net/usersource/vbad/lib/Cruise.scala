package net.usersource.vbad.lib

import xml.{XML, Elem, Node}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.DateTime
import collection.mutable.ListBuffer



case class CruiseJob( name: String, status: String, timestamp: DateTime )

case class CruiseStage( name: String, jobs: List[CruiseJob] ) {

  val colour = {
    var c = "green"
    jobs.foreach( job => job.status match {
      case "Success" => {}
      case "Failure" => c = "red"
      case _ => if( c != "red" ) c = "yellow"
    })
    c
  }

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
