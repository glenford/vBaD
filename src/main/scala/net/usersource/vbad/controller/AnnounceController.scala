package net.usersource.vbad.controller

import net.liftweb.actor.LiftActor
import net.usersource.tts.Say


class AnnounceController extends LiftActor {

  def messageHandler = {
    case text: String => {
      Say(text)
    }
  }

}

object AnnounceController {
  val actor = new AnnounceController
}