package net.usersource.vbad.snippet

import net.liftweb.mapper.{Ascending, OrderBy}
import net.usersource.vbad.model.Site
import net.liftweb.http.SHtml._
import net.liftweb.http.S._
import net.liftweb.util.Helpers._
import net.liftweb.http.RequestVar
import net.liftweb.common.{Empty, Box, Full}
import xml.{Group, Text, NodeSeq}


class SiteConfig {

  private object selectedSite extends RequestVar[Box[Site]](Empty)

  def sites: NodeSeq = {
    <h3>List of Sites</h3> :: <br/> ::
    <tr>{Site.htmlHeaders}<th>Edit</th><th>Delete</th></tr> ::
      Site.findAll(OrderBy(Site.id, Ascending)).flatMap(site =>
        <tr>{site.htmlLine}
          <td>{link("/site/edit", () => selectedSite(Full(site)), Text("Edit"))}</td>
          <td>{link("/site/delete", () => selectedSite(Full(site)), Text("Delete"))}</td>
        </tr>)
  }

  private def saveSite(site: Site) = site.validate match {
    case Nil => site.save; redirectTo("/site/index")
    case x => error(x); selectedSite(Full(site))
  }

  def add(in: NodeSeq): NodeSeq = {
    selectedSite.is.openOr(new Site).toForm(Empty, saveSite _) ++ <tr>
      <td><a href="/site/index">Cancel</a></td>
      <td><input type="submit" value="Create"/></td>
    </tr>
  }

  def confirmDelete(xhtml: Group): NodeSeq = {
    (for (site <- selectedSite.is) // find the user
     yield {
        def deleteSite() {
          site.delete_!
          redirectTo("/site/index")
        }
        bind("xmp", xhtml, "name" -> (site.name.is),"delete" -> submit("Delete", deleteSite _))
      }) openOr {error("Platform not found"); redirectTo("/site/index")}
  }

  def edit(xhtml: Group): NodeSeq =
    selectedSite.map(_.toForm(Empty, saveSite _) ++ <tr>
      <td><a href="/site/index">Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
  ) openOr {error("Site not found"); redirectTo("/site/index")}
}