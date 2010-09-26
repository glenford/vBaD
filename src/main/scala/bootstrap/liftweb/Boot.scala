package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import net.usersource.vbad.model.{Build, Site, CIPlatform}
import net.usersource.vbad.comet.{TickerFrame, CurrentTime, IFrameRotate, TimedIFrameRotate, BuildStatusRotate}

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
      case CometCreationInfo("RotatingBuildStatus",
                             name,
                             defaultXml,
                             attributes,
                             session) => {
                               new BuildStatusRotate(session, Full("RotatingBuildStatus"),name, defaultXml, attributes)
      }
      case CometCreationInfo("RotatingIFrame",
                             name,
                             defaultXml,
                             attributes,
                             session) => {
                               new IFrameRotate(session, Full("RotatingIFrame"),name, defaultXml, attributes)
      }
      case CometCreationInfo("RotatingTimedIFrame",
                             name,
                             defaultXml,
                             attributes,
                             session) => {
                               new TimedIFrameRotate(session, Full("RotatingTimedIFrame"),name, defaultXml, attributes)
      }
      case CometCreationInfo("CurrentTime",
                             name,
                             defaultXml,
                             attributes,
                             session) => {
                               new CurrentTime(session, Full("CurrentTime"),name, defaultXml, attributes)
      }

      case CometCreationInfo("TickerFrame",
                             name,
                             defaultXml,
                             attributes,
                             session) => {
                               new TickerFrame(session, Full("TickerFrame"),name, defaultXml, attributes)
      }
    }

  }

  def boot {
    setupDatabase
    setupSchemify
    setupLiftSnippets
    setupComet
  }
}
