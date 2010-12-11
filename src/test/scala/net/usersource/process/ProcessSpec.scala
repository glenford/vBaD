package net.usersource.process

import org.scalatest.matchers.MustMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}

class ProcessSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  feature("able to execute an external process") {
    scenario("able to get return code") {
      given("a valid local script")
      val script = "src/test/resources/process_spec_script.sh"

      when("it is executed")
      val process = Process(script) run

      then("the expected return code is captured")
      process.waitTillDone
      process.returnCode.get must be === 0
    }

    scenario("able to get a specific return code") {
      given("a valid local script")
      val script = "src/test/resources/process_spec_script.sh"

      when("it is executed")
      val process = Process(script,"exit","3") run

      then("the expected return code is captured")
      process.waitTillDone
      process.returnCode.get must be === 3
    }
  }

}