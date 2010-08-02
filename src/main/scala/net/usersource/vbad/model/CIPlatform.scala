package net.usersource.vbad.model

import net.liftweb.mapper._

object CIPlatform extends CIPlatform with LongKeyedMetaMapper[CIPlatform]


class CIPlatform extends LongKeyedMapper[CIPlatform] with IdPK {
  def getSingleton = CIPlatform

  object name extends MappedString(this, 32)
  object platform extends MappedString(this, 32)
  object username extends MappedString(this, 32)
  object password extends MappedPassword(this)
  object url extends MappedString(this, 1024)

}