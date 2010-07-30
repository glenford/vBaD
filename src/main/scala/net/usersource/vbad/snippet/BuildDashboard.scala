package net.usersource.vbad.snippet

import net.usersource.vbad.controller.BuildCollector


class BuildDashboard {

  def show = {
    <span id="build_list">
      {
      for(build <- BuildCollector.builds) yield {
        <span id="build">
          <span id="build_name">{build.name}</span>
          <span id="build_status">{build.status}</span>
          <span id="build_timestamp">{build.timestamp}</span>
          <span id="build_user">{build.user}</span>
      </span> }
      }
    </span>
  }
}