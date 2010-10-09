
package net.usersource.vbad.controller

import net.liftweb.actor.LiftActor
import net.liftweb.util.ActorPing
import net.liftweb.util.Helpers._
import net.usersource.http.simple.{Http,Response}
import net.usersource.vbad.model.{CIPlatform, Build}
import net.usersource.vbad.lib.{BuildResults, CruisePipeline}

case object GetBuildStatuses
case object UpdateBuildStatuses

class BuildCollector extends LiftActor {

  var builds: List[BuildResults] = Nil

  updateBuilds
  ActorPing.schedule(this, UpdateBuildStatuses, 60 seconds)


  def messageHandler = {
    case GetBuildStatuses => reply(getBuilds)
    case UpdateBuildStatuses => {
      updateBuilds
      ActorPing.schedule(this, UpdateBuildStatuses, 60 seconds)
    }
  }

  def getPlatformResponse( platform: CIPlatform ): Response = {
    if( platform.username.is.nonEmpty ) {
      println("attempting access to [" + platform.url + "][" + platform.username + "][" + platform.password + "]" )
      Http get( platform.url, platform.username, platform.password )
    }
    else {
      println("attempting access to [" + platform.url + "]")
      Http get platform.url
    }
  }

  def getBuild( name: String, platform: CIPlatform ): Option[BuildResults] = {
    try {
      val response = getPlatformResponse(platform)
      if( response.status == 200 ) {
        platform.platform.is.toUpperCase match {
          case "CRUISE" | "GO" => { CruisePipeline(name,response.body.get) }
          case "HUDSON" => { None }
          case _ => { None }
        }
      }
      else {
        println("Error [" + response.status +"] response from [" + platform.url + "]")
        None
      }
    }
    catch {
      case e:Exception => { println( "Exception in getting platform: " + e)
                            None }
      case _ => None
    }
  }

  def updateBuilds = {
    var listOfBuildStatus: List[BuildResults] = Nil
    val buildList = Build.findAll
    buildList.foreach( build => {
      val buildStatus = getBuild( build.buildName.is, CIPlatform.find(build.platform).get)
      if( buildStatus.isDefined ) listOfBuildStatus = buildStatus.get :: listOfBuildStatus
    })
    builds = listOfBuildStatus
  }

  def getBuilds: List[BuildResults] = {
    builds
  }

}

object BuildCollector {
  val actor = new BuildCollector
  
  def builds: List[BuildResults] = { (actor !! GetBuildStatuses).asA[List[BuildResults]].getOrElse(Nil) }
}

