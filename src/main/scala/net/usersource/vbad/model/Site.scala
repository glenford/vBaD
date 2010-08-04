package net.usersource.vbad.model

import net.liftweb.mapper.{MappedString, LongKeyedMetaMapper, LongKeyedMapper, IdPK}


object Site extends Site with LongKeyedMetaMapper[Site]

class Site extends LongKeyedMapper[Site] with IdPK {
  def getSingleton = Site

  object name extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Name must be more than 2 characters") _ :: super.validations }
  }

  object url extends MappedString(this, 1024) {
    override def validations = { valMinLen(10,"Platform must be more than 10 characters") _ :: super.validations }
  }
}