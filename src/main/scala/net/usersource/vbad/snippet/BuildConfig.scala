package net.usersource.vbad.snippet

import net.liftweb.http.RequestVar
import net.liftweb.mapper.{Ascending, OrderBy}
import xml.{Text, NodeSeq}
import net.liftweb.http.SHtml._
import net.liftweb.http.S._
import net.liftweb.util.Helpers._
import net.liftweb.common.{Full, Empty, Box}
import net.usersource.vbad.model.Build


class BuildConfig {

  private object selectedBuild extends RequestVar[Box[Build]](Empty)

  def builds: NodeSeq = {
    <h3>List of Builds</h3> :: <br/> ::
    <tr>{Build.htmlHeaders}<th>Edit</th><th>Delete</th></tr> ::
      Build.findAll(OrderBy(Build.id, Ascending)).flatMap(build =>
        <tr>{build.htmlLine}
          <td>{link("/build/edit", () => selectedBuild(Full(build)), Text("Edit"))}</td>
          <td>{link("/build/delete", () => selectedBuild(Full(build)), Text("Delete"))}</td>
        </tr>)
  }

  private def saveBuild(build: Build) = build.validate match {
    case Nil => build.save; redirectTo("/build/index")
    case x => error(x); selectedBuild(Full(build))
  }

  def add(in: NodeSeq): NodeSeq = {
    selectedBuild.is.openOr(new Build).toForm(Empty, saveBuild _) ++ <tr>
      <td><a href="/build/index">Cancel</a></td>
      <td><input type="submit" value="Create"/></td>
    </tr>
  }

}