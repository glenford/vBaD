package net.usersource.process


class Process( val args: List[String] ) {

  def run: Process = { this }

  def waitTillDone: Process = { this }

  def returnCode = { -1 }

}

object Process {

  def apply( args: String* ): Process = {
    new Process( args.toList )
  }

}