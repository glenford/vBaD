package net.usersource.vbad.comet

import net.liftweb.http.js.JsCmds.SetHtml
import scala.xml.NodeSeq
import net.liftweb.common.{Full, Box}
import net.liftweb.http.{CometActor, LiftSession}
import net.liftweb.util.ActorPing
import net.liftweb.util.Helpers._
import org.joda.time.{DateTimeZone, DateTime}
import org.joda.time.format.DateTimeFormatterBuilder
import util.Random

class TickerFrame(initSession: LiftSession,
                  initType: Box[String],
                  initName: Box[String],
                  initDefaultXml: NodeSeq,
                  initAttributes: Map[String, String]) extends CometActor {

  val tickerData: List[String] = List(
                 "When Chuck Norris throws exceptions, it’s across the room. ",
                 "All arrays Chuck Norris declares are of infinite size, because Chuck Norris knows no bounds.",
                 "Chuck Norris doesn’t have disk latency because the hard drive knows to hurry the hell up.",
                 "Chuck Norris writes code that optimizes itself.",
                 "Chuck Norris can’t test for equality because he has no equal.",
                 "Chuck Norris’s first program was kill -9.")

  val rand = new Random(System.currentTimeMillis)

  override def defaultPrefix = Full("vbad")

  setPingIn

  private lazy val spanId = uniqueId + "_vbad_ticker"

  def setPingIn = ActorPing.schedule(this, Tick, 45 seconds)

  def render = {
    bind("tickerFrame" -> span)
  }

  def getTicker = {

    def ticker = tickerData(rand.nextInt(tickerData.length-1))

    <span id="ticker">
      <marquee scrolldelay="40" scrollamount="3">{ticker}</marquee>
    </span>
  }

  def span = (<span id={spanId}>{getTicker}</span>)

  override def lowPriority = {
    case Tick =>
      partialUpdate(SetHtml(spanId, getTicker))
      setPingIn
  }

  // need to investigate this, why is this needed
  def this( initSession: LiftSession, initName:Box[String],initDefaultXml:NodeSeq,initAttributes:Map[String,String] ) =
          this( initSession, Box("TickerFrame"), initName, initDefaultXml, initAttributes )

  initCometActor(initSession, initType, initName, initDefaultXml, initAttributes)
}