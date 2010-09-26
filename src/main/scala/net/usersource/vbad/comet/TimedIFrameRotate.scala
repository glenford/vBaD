package net.usersource.vbad.comet

import net.liftweb.http._
import js.JE.JsRaw
import js.JsCmd
import net.liftweb.http.js.JsCmds.OnLoad
import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import scala.xml.{Text, NodeSeq}
import net.usersource.vbad.model.Site

class TimedIFrameRotate(initSession: LiftSession,
                        initType: Box[String],
                        initName: Box[String],
                        initDefaultXml: NodeSeq,
                        initAttributes: Map[String, String]) extends CometActor {

  case object Tick  

  override def defaultPrefix = Full("vbad")

  var frameNumber = 0
  setPingIn

  def setPingIn = ActorPing.schedule(this, Tick, 60 seconds)

  def render = {
    jscmd
  }

  def jscmd: JsCmd = { OnLoad(JsRaw("load_into_frame( 'http://" + nextUrl +"','theiframe','observerpane')").cmd) }

  def nextUrl = {
    val sites = Site.findAll()
    if( sites.length > 0 ) {
      frameNumber = frameNumber + 1
      if( frameNumber >= sites.length ) frameNumber = 0;
	    sites(frameNumber).url.get
    }
    else {
      "_blank"
    }
  }

  override def lowPriority = {
    case Tick =>
      partialUpdate( jscmd )
      setPingIn
  }

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}

