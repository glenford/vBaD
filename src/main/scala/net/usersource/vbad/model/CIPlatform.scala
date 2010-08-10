package net.usersource.vbad.model

import net.liftweb.mapper._
import net.liftweb.util.BaseField


object CIPlatform extends CIPlatform with LongKeyedMetaMapper[CIPlatform]


class CIPlatform extends LongKeyedMapper[CIPlatform] with IdPK with OneToMany[Long, CIPlatform] {
  def getSingleton = CIPlatform

  object name extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Name must be more than 2 characters") _ :: super.validations }
  }

  object platform extends MappedString(this, 32) {
    override def validations = { valMinLen(2,"Platform must be more than 2 characters") _ :: super.validations }
  }

  object username extends MappedString(this, 32)

  object password extends MappedString(this, 32)

  object url extends MappedString(this, 1024) {
    override def validations = { valMinLen(10,"Platform must be more than 10 characters") _ :: super.validations }
  }

  object builds extends MappedOneToMany(Build, Build.platform, OrderBy(Build.name, Ascending))
                with Owned[Build] with Cascade[Build]

}