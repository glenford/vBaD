package net.usersource.process

import org.scalatest.matchers.MustMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}

class ProcessSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("able to execute an external process") {

    scenario("a local script") {

      given("a valid local script")
      val script = "src/test/resources/process_spec_script.sh"

      when("it is executed")
      val process = Process(script) run

      then("the expected return code is captured")
      process.waitTillDone
      process.returnCode.get must be === 0
    }
  }

}