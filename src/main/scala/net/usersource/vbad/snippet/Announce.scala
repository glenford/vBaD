package net.usersource.vbad.snippet

import net.usersource.tts.Say
import net.usersource.vbad.controller.AnnounceController

import net.liftweb.http.StatefulSnippet
import _root_.net.liftweb._
import http._
import SHtml._
import js._
import JsCmds._
import common._
import util._
import Helpers._

import xml.NodeSeq


class Announce extends StatefulSnippet {

  private var text = ""
  private val from = S.referer openOr "/"

  def dispatch = { case _ => render _ }

  def render( xhtml: NodeSeq ):NodeSeq = {
    def validate() {
      (text.length) match {
        case (f) if f < 2 => S.error("First and last names too short")
        case _ => {
          AnnounceController.actor ! text
        }
      }
    }

    bind("form", xhtml,
         "text" -> textAjaxTest(text, s => text = s, s => {S.notice("Last name "+text); Noop}),
         "submit" -> submit("Send", validate _)
    )
  }
}
