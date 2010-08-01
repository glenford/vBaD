package net.usersource.vbad.snippet

import xml.{NodeSeq,Text}
import net.usersource.vbad.model.CIPlatform
import net.liftweb.mapper.{Ascending, OrderBy}
import net.liftweb.http.SHtml._
import net.liftweb.common.{Box, Full, Empty}
import net.liftweb.http.RequestVar

class CI {

  private object selectedCIPlatform extends RequestVar[Box[CIPlatform]](Empty)

  def platforms: NodeSeq = {

    CIPlatform.find() match {
      case Empty => CIPlatform.create.name("bonus-cruise").platform("cruise").username("admin").password("unib3t").url("http://").save
      case _ => ;
    }

    <h3>List of Platforms</h3> ::
    <tr>{CIPlatform.htmlHeaders}<th>Edit</th><th>Delete</th></tr> ::
      CIPlatform.findAll(OrderBy(CIPlatform.id, Ascending)).flatMap(p =>
        <tr>{p.htmlLine}
          <td>{link("/ci/edit", () => selectedCIPlatform(Full(p)), Text("Edit"))}</td>
          <td>{link("/ci/delete", () => selectedCIPlatform(Full(p)), Text("Delete"))}</td>
        </tr>)

  }

  def add = { }
}