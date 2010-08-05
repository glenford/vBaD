package net.usersource.vbad.comet

import _root_.net.liftweb._
import http._
import common._
import util._
import Helpers._
import js._
import JsCmds._
import _root_.scala.xml.{Text, NodeSeq}
import net.usersource.vbad.model.Site

class BuildStatusRotate(initSession: LiftSession,
                        initType: Box[String],
                        initName: Box[String],
                        initDefaultXml: NodeSeq,
                        initAttributes: Map[String, String]) extends CometActor {

  override def defaultPrefix = Full("vbad")

  ActorPing.schedule(this, Tick, 60 seconds)

  private lazy val spanId = uniqueId + "_vbad_build_status"

  private var frameNumber = 0;

  def render = {
    bind("build_status" -> timeSpan)
  }

  def getBuildStatus = {
    <p>Build Status goes here...</p>
  }

  def timeSpan = (<span id={spanId}>{getBuildStatus}</span>)

  override def lowPriority = {
    case Tick =>
      partialUpdate(SetHtml(spanId, getBuildStatus))
      ActorPing.schedule(this, Tick, 60 seconds)
  }

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}



