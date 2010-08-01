package net.usersource.vbad.controller


import net.usersource.vbad.model.BuildStatus
import net.liftweb.actor.LiftActor


case object GetBuildStatuses
case object UpdateBuildStatuses


class BuildCollector extends LiftActor {

  var builds: List[BuildStatus] = Nil
  builds = new BuildStatus("bonus", "passed", "now", "me") :: builds
  builds = new BuildStatus("crm", "passed", "1 hr ago", "jimbo") :: builds
  builds = new BuildStatus("goalwire", "failed", "3 days ago", "lance") :: builds


  def messageHandler = {
    case GetBuildStatuses => reply(getBuilds)
    case UpdateBuildStatuses => update
  }

  def update = {
    // periodic upate tick
  }

  def getBuilds: List[BuildStatus] = {
    builds
  }

}

object BuildCollector {
  val actor = new BuildCollector
  
  def builds: List[BuildStatus] = { (actor !! GetBuildStatuses).asA[List[BuildStatus]].get }
}

