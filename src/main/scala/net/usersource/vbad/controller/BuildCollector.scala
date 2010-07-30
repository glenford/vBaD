package net.usersource.vbad.controller


import net.usersource.vbad.model.BuildStatus


class BuildCollector {

  


}

object BuildCollector {

  var builds: List[BuildStatus] = Nil
  builds = new BuildStatus("bonus", "passed", "now", "me") :: builds
  builds = new BuildStatus("crm", "passed", "1 hr ago", "jimbo") :: builds
  builds = new BuildStatus("goalwire", "failed", "3 days ago", "lance") :: builds

}

