package net.usersource.vbad.lib

class BuildStatus {
   var name: String = "unknown"
   var status: String = "unknown"
   var timestamp: String = "unknown"
   var user: String = "unknown"

  def this( name:String, status: String, timestamp: String, user: String ) = {
    this()
    this.name = name
    this.status = status
    this.timestamp = timestamp
    this.user = user
  }
}