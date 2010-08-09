
package net.usersource.vbad.comet

import net.liftweb.http.js.JsCmds.SetHtml
import scala.xml.NodeSeq
import net.liftweb.common.{Full, Box}
import net.liftweb.http.{CometActor, LiftSession}
import net.liftweb.util.ActorPing
import net.liftweb.util.Helpers._
import net.usersource.vbad.controller.BuildCollector


class BuildStatusRotate(initSession: LiftSession,
                        initType: Box[String],
                        initName: Box[String],
                        initDefaultXml: NodeSeq,
                        initAttributes: Map[String, String]) extends CometActor {


  override def defaultPrefix = Full("vbad")

  ActorPing.schedule(this, Tick, 60 seconds)

  private lazy val spanId = uniqueId + "_vbad_build_status"

  def render = {
    bind("buildStatus" -> span)
  }

  def getBuildStatus = {
    <span id="build_list">
      {
      for(build <- BuildCollector.builds) yield {
        <span id="build">
          <span id="build_name">{build.name}</span>
          <span id="build_status">{build.status}</span>
          <span id="build_timestamp">{build.timestamp}</span>
          <span id="build_user">{build.user}</span>
      </span> }
      }
    </span>
  }

  def span = (<span id={spanId}>{getBuildStatus}</span>)

  override def lowPriority = {
    case Tick =>
      partialUpdate(SetHtml(spanId, getBuildStatus))
      ActorPing.schedule(this, Tick, 60 seconds)
  }

  // need to investigate this, why is this needed
  def this( initSession: LiftSession, initName:Box[String],initDefaultXml:NodeSeq,initAttributes:Map[String,String] ) =
          this( initSession, Box("BuildStatusRotate"), initName, initDefaultXml, initAttributes )

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}


