package net.usersource.vbad.lib

class BuildStatus( val name:String, val status: String, val timestamp: String ) {

  override def toString: String = {
    name + " : " + status + " : " + timestamp
  }
}