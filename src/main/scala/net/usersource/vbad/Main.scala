package net.usersource.vbad

import org.mortbay.jetty.Server
import org.mortbay.jetty.servlet.Context
import org.mortbay.jetty.webapp.WebAppContext


object Main {

  val serverPort = System.getProperty("VBAD_PORT", "8080").toInt

  def main( args: Array[String] ): Unit = {
    val server = new Server(serverPort)
    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setWar("src/main/webapp")
    //val context = new Context(_server, "/", Context.SESSIONS)
    //context.addFilter(new FilterHolder(new LiftFilter()), "/");
    server.addHandler(context)
    server.start
    server.join
  }

}