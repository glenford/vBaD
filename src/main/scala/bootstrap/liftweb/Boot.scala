package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import net.usersource.vbad.comet._
import net.usersource.vbad.model.{Build, Site, CIPlatform}

class Boot {

  private def setupDatabase = {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			                 Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			                 Props.get("db.user"),
                       Props.get("db.password"))
      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)
      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }
  }

  private def setupSchemify = {
    Schemifier.schemify(true, Schemifier.infoF _, CIPlatform)
    Schemifier.schemify(true, Schemifier.infoF _, Site)
    Schemifier.schemify(true, Schemifier.infoF _, Build)
  }

  private def setupLiftSnippets = {
    LiftRules.addToPackages("net.usersource.vbad")
  }

  private def setupComet = {
    LiftRules.cometCreation.append {
      case CometCreationInfo("RotatingIFrame",
                             name,
                             defaultXml,
                             attributes,
                             session) =>
                               new IFrameRotate(session, Full("RotatingIFrame"),
                                                name, defaultXml, attributes)
    }
  }

  def boot {
    setupDatabase
    setupSchemify
    setupLiftSnippets
    setupComet
  }
}
