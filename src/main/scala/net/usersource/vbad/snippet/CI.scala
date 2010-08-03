package net.usersource.vbad.snippet

import net.usersource.vbad.model.CIPlatform
import net.liftweb.mapper.{Ascending, OrderBy}
import net.liftweb.http.SHtml._
import net.liftweb.http.S._
import net.liftweb.util.Helpers._
import net.liftweb.common.{Box, Full, Empty}
import net.liftweb.http.RequestVar
import xml.{Group, NodeSeq, Text}

class CI {

  private object selectedCIPlatform extends RequestVar[Box[CIPlatform]](Empty)

  def platforms: NodeSeq = {

    <h3>List of Platforms</h3> :: <br/> ::
    <tr>{CIPlatform.htmlHeaders}<th>Edit</th><th>Delete</th></tr> ::
      CIPlatform.findAll(OrderBy(CIPlatform.id, Ascending)).flatMap(p =>
        <tr>{p.htmlLine}
          <td>{link("/ci/edit", () => selectedCIPlatform(Full(p)), Text("Edit"))}</td>
          <td>{link("/ci/delete", () => selectedCIPlatform(Full(p)), Text("Delete"))}</td>
        </tr>)
  }

  private def saveCIPlatform(platform: CIPlatform) = platform.validate match {
    case Nil => platform.save; redirectTo("/ci/index")
    case x => error(x); selectedCIPlatform(Full(platform))
  }

  def add(in: NodeSeq): NodeSeq = {
    selectedCIPlatform.is.openOr(new CIPlatform).toForm(Empty, saveCIPlatform _) ++ <tr>
      <td><a href="/ci/index">Cancel</a></td>
      <td><input type="submit" value="Create"/></td>
    </tr>
  }

  def confirmDelete(xhtml: Group): NodeSeq = {
    (for (platform <- selectedCIPlatform.is) // find the user
     yield {
        def deleteCIPlatform() {
          platform.delete_!
          redirectTo("/ci/index")
        }
        bind("xmp", xhtml, "name" -> (platform.name.is),"delete" -> submit("Delete", deleteCIPlatform _))
      }) openOr {error("Platform not found"); redirectTo("/ci/index")}
  }

  def edit(xhtml: Group): NodeSeq =
    selectedCIPlatform.map(_.toForm(Empty, saveCIPlatform _) ++ <tr>
      <td><a href="/ci/index">Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
  ) openOr {error("User not found"); redirectTo("/ci/index")}
}