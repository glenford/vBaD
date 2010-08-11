
package net.usersource.vbad.controller

import net.liftweb.actor.LiftActor
import net.liftweb.util.ActorPing
import net.liftweb.util.Helpers._
import net.usersource.http.simple.{Http,Response}
import net.usersource.vbad.model.{CIPlatform, Build}
import net.usersource.vbad.lib.{HudsonBuildFactory, CruiseBuildFactory, BuildStatus}

case object GetBuildStatuses
case object UpdateBuildStatuses

class BuildCollector extends LiftActor {

  var builds: List[BuildStatus] = Nil

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
      println( "attempting access to [" + platform.url + "][" + platform.username + "][" + platform.password + "]" )
      Http get( platform.url, platform.username, platform.password )
    }
    else {
      Http get platform.url
    }
  }

  def getBuild( name: String, platform: CIPlatform ): Option[BuildStatus] = {
    try {
    val response = getPlatformResponse(platform)
    if( response.status == 200 ) {
      if( platform.platform.is.equalsIgnoreCase("cruise") ) {
        CruiseBuildFactory.build(name,response.body.get)
      }
      else if( platform.platform.is.equalsIgnoreCase("hudson") ) {
        HudsonBuildFactory.build(name,response.body.get)
      }
      else {
        println("platform not recognised : " + platform.platform.is)
        None
      }
    }
    else {
      println("no valid response")
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
    var listOfBuildStatus: List[BuildStatus] = Nil
    val buildList = Build.findAll
    buildList.foreach( build => {
      val buildStatus = getBuild( build.buildName.is, CIPlatform.find(build.platform).get)
      if( buildStatus.isDefined )
        listOfBuildStatus = buildStatus.get :: listOfBuildStatus
    })
    builds = listOfBuildStatus
  }

  def getBuilds: List[BuildStatus] = {
    builds
  }

}

object BuildCollector {
  val actor = new BuildCollector
  
  def builds: List[BuildStatus] = { (actor !! GetBuildStatuses).asA[List[BuildStatus]].get }
}

