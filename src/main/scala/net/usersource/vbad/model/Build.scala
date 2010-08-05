package net.usersource.vbad.model

import net.liftweb.mapper._
import net.liftweb.common.Full

object Build extends Build with LongKeyedMetaMapper[Build]

class Build extends LongKeyedMapper[Build] with IdPK {
  def getSingleton = Build

  object name extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Name must be more than 2 characters") _ :: super.validations }
  }

  object buildName extends MappedString(this, 256) {
    override def validations = { valMinLen(10,"Build Name must be more than 2 characters") _ :: super.validations }
  }

  object platform extends LongMappedMapper(this, CIPlatform) {
    override def validSelectValues = Full(CIPlatform.findAll.map(x => (x.id.is, x.name.is)))
  }

}