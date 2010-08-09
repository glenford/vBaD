
package net.usersource.vbad.controller

import net.liftweb.actor.LiftActor
import net.usersource.vbad.lib.BuildStatus
import net.liftweb.util.ActorPing
import net.liftweb.util.Helpers._



case object GetBuildStatuses
case object UpdateBuildStatuses

class BuildCollector extends LiftActor {

  case object Update

  var builds: List[BuildStatus] = Nil
  builds = new BuildStatus("bonus", "passed", "now", "me") :: builds
  builds = new BuildStatus("crm", "passed", "1 hr ago", "jimbo") :: builds
  builds = new BuildStatus("goalwire", "failed", "3 days ago", "lance") :: builds

  ActorPing.schedule(this, Update, 60 seconds)


  def messageHandler = {
    case GetBuildStatuses => reply(getBuilds)
    case UpdateBuildStatuses => {
      updateBuilds
      ActorPing.schedule(this, Update, 60 seconds)
    }
  }

  def updateBuilds = {
    println("updating list of builds")
    // rebuild the list of builds
  }

  def getBuilds: List[BuildStatus] = {
    builds
  }

}

object BuildCollector {
  val actor = new BuildCollector
  
  def builds: List[BuildStatus] = { (actor !! GetBuildStatuses).asA[List[BuildStatus]].get }
}

