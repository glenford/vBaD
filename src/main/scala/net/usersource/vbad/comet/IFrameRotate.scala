package net.usersource.vbad.comet {

import _root_.net.liftweb._
import http._
import common._
import util._
import Helpers._
import js._
import JsCmds._
import _root_.scala.xml.{Text, NodeSeq}

class IFrameRotate(initSession: LiftSession,
                   initType: Box[String],
                   initName: Box[String],
                   initDefaultXml: NodeSeq,
                   initAttributes: Map[String, String]) extends CometActor {

  override def defaultPrefix = Full("vbad")

  ActorPing.schedule(this, Tick, 60 seconds)

  private lazy val spanId = uniqueId+"_vbad_iframe"

  private var frameNumber = 1;

  def render = {
    bind("iframe" -> timeSpan)
  }

  def getIFrame = {
	frameNumber match {
		case 1 => {
			 frameNumber = 2
                         <iframe src="http://www.google.com" width="1024" height="768"></iframe>
		}
                case 2 => {
			frameNumber = 3
                        <iframe src="http://www.yahoo.com" width="1024" height="768"></iframe>
		}
                case 3 => {
			frameNumber = 1
                        <iframe src="http://www.microsoft.com" width="1024" height="768"></iframe>
		}
	}
  }

  def timeSpan = (<span id={spanId}>{getIFrame}</span>)

  override def lowPriority = {
    case Tick =>
      partialUpdate(SetHtml(spanId, getIFrame))
      ActorPing.schedule(this, Tick, 60 seconds)
  }

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}

case object Tick
}

