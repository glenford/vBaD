package net.usersource.vbad.model

import net.liftweb.mapper._
import net.liftweb.util.BaseField


object CIPlatform extends CIPlatform with LongKeyedMetaMapper[CIPlatform]


class CIPlatform extends LongKeyedMapper[CIPlatform] with IdPK {
  def getSingleton = CIPlatform

  object name extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Name must be more than 2 characters") _ :: super.validations }
  }

  object platform extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Platform must be more than 2 characters") _ :: super.validations }
  }

  object username extends MappedString(this, 32)

  object password extends MappedPassword(this) {
    override def validate = { Nil }  // remove validation of password
  }

  object url extends MappedString(this, 1024) {
    override def validations = { valMinLen(10,"Platform must be more than 10 characters") _ :: super.validations }
  }

}