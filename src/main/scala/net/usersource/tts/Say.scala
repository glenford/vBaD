package net.usersource.tts

import net.usersource.process.Process


object Say {

  val exe = "/usr/bin/say"

  def apply( text: String ) = {
    val process = Process(exe,text).run.waitTillDone
  }
  
}