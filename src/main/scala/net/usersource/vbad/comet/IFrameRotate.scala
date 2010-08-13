package net.usersource.vbad.comet


import net.liftweb.http._
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import scala.xml.{Text, NodeSeq}
import net.usersource.vbad.model.Site

class IFrameRotate(initSession: LiftSession,
                   initType: Box[String],
                   initName: Box[String],
                   initDefaultXml: NodeSeq,
                   initAttributes: Map[String, String]) extends CometActor {

  override def defaultPrefix = Full("vbad")
  private lazy val spanId = uniqueId + "_vbad_iframe"
  var frameNumber = 0
  setPingIn


  def setPingIn = ActorPing.schedule(this, Tick, 60 seconds)

  def render = {
    bind("iframe" -> timeSpan)
  }

  def getIFrame = {
    val sites = Site.findAll()
    if( sites.length > 0 ) {
      frameNumber = frameNumber + 1
      if( frameNumber >= sites.length ) frameNumber = 0;
	    <iframe src={sites(frameNumber).url.get} width="1024" height="768"></iframe>
    }
    else {
      <iframe src="http://github.com/glenford/vBaD" width="1024" height="768">No Sites Configured</iframe>
    }
  }

  def timeSpan = (<span id={spanId}>{getIFrame}</span>)

  override def lowPriority = {
    case Tick =>
      partialUpdate(SetHtml(spanId, getIFrame))
      setPingIn
  }

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}

case object Tick

