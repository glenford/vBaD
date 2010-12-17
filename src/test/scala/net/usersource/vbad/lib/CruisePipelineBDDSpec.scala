package net.usersource.vbad.lib

import org.scalatest.matchers.MustMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.joda.time.format.DateTimeFormat


class CruisePipelineBDDSpec extends FeatureSpec with GivenWhenThen with MustMatchers {

  val data = <Projects>
                 <Project name="PipelineA :: Stage1" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/BuildAndUnitTest/1" />
                 <Project name="PipelineA :: Stage1 :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/BuildAndUnitTest/1/build" />
                 <Project name="PipelineA :: Stage2" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/IntegrationTest/1" />
                 <Project name="PipelineA :: Stage2 :: integration" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/IntegrationTest/1/IntegrationTest" />
                 <Project name="PipelineA :: Stage3" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/CodeCoverage/1" />
                 <Project name="PipelineA :: Stage3 :: coverage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/CodeCoverage/1/sonar" />
                 <Project name="PipelineA :: Stage4" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/Deploy/1" />
                 <Project name="PipelineA :: Stage4 :: deploy" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/Deploy/1/deploy-artifacts" />
                 <Project name="PipelineA :: Stage5" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/SeleniumTest/1" />
                 <Project name="PipelineA :: Stage5 :: accept" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/SeleniumTest/1/SeleniumTest" />
                 <Project name="PipelineA :: Stage6" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/106/PerformanceTest/1" />
                 <Project name="PipelineA :: Stage6 :: performance" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/106/PerformanceTest/1/PT" />
                 <Project name="PipelineB :: InitialStage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusTestTool/6/BuildUnitTest/1" />
                 <Project name="PipelineB :: InitialStage :: buildAndUnitTest" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusTestTool/6/BuildUnitTest/1/build" />
               </Projects>

  val dataIssueOne = <Projects>
      <Project name="BonusSystem :: BuildAndUnitTest" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1 :: 2" lastBuildTime="2010-12-16T18:20:20" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/1/BuildAndUnitTest/2"/>
      <Project name="BonusSystem :: BuildAndUnitTest :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1 :: 2" lastBuildTime="2010-12-16T18:20:20" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/1/BuildAndUnitTest/2/build"/>
      <Project name="BonusSystem :: IntegrationTest" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1" lastBuildTime="2010-12-16T18:21:15" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/1/IntegrationTest/1"/>
      <Project name="BonusSystem :: IntegrationTest :: IntegrationTest" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1" lastBuildTime="2010-12-16T18:21:15" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/1/IntegrationTest/1/IntegrationTest"/>
      <Project name="BonusSystem :: CodeCoverage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1" lastBuildTime="2010-12-16T18:42:19" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/1/CodeCoverage/1"/>
      <Project name="BonusSystem :: CodeCoverage :: sonar" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="1" lastBuildTime="2010-12-16T18:42:19" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/1/CodeCoverage/1/sonar"/>
      <Project name="BonusTestTool :: BuildUnitTest" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="2" lastBuildTime="2010-12-16T18:24:25" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusTestTool/2/BuildUnitTest/1"/>
      <Project name="BonusTestTool :: BuildUnitTest :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="2" lastBuildTime="2010-12-16T18:24:25" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusTestTool/2/BuildUnitTest/1/build"/>
  </Projects>

  feature("can create a pipeline from xml") {

    scenario("process a pipeline from the xml") {
      given("a valid xml")

      when("a pipleine is extracted")
      val pipeline = CruisePipeline("PipelineA", data.toString).get

      then("the extacted name will be correct")
      pipeline.name must be === "PipelineA"


      and("the length will be correct")
      pipeline.stages.length must be === 6
    }

    scenario("process a pipeline from the xml with partial build values") {
      given("a valid xml")

      when("a pipleine is extracted")
      val pipeline = CruisePipeline("BonusSystem", dataIssueOne.toString).get

      then("the extacted name will be correct")
      pipeline.name must be === "BonusSystem"


      and("the length will be correct")
      pipeline.stages.length must be === 3
    }

    scenario("process a different pipeline from the xml") {
      given("a valid xml")

      when("a pipleine is extracted")
      val pipeline = CruisePipeline("PipelineB", data.toString).get

      then("the extacted name will be correct")
      pipeline.name must be === "PipelineB"

      and("the length will be correct")
      pipeline.stages.length must be === 1

      and("the stage name will be correct")
      pipeline.stages(0).name must be === "InitialStage"

      and("the number of jobs will be correct")
      pipeline.stages(0).jobs.length must be === 1

      and("the name of the job will be correct")
      pipeline.stages(0).jobs(0).name must be === "buildAndUnitTest"
    }

    scenario("correct job information is reflected in the extracted pipeline") {
      given("a valid xml")

      when("a pipeline is extracted")
      val pipeline = CruisePipeline("PipelineB", data.toString).get

      then("the job details will be correct")
      pipeline.stages(0).jobs(0).name must be === "buildAndUnitTest"
      pipeline.stages(0).jobs(0).status must be === "Success"
      pipeline.stages(0).jobs(0).timestamp must be === DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2010-08-17 19:03:35")
    }

    scenario("able to deal with a single pipeline, stage and job") {
      given("a valid xml")
      val simplePipeline = <Projects>
                             <Project name="vBaD :: defaultStage" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/pipelines/vBaD/6/defaultStage/1" />
                             <Project name="vBaD :: defaultStage :: defaultJob" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/tab/build/detail/vBaD/6/defaultStage/1/defaultJob" />
                           </Projects>

      when("a pipeline is extracted")
      val pipeline = CruisePipeline("vBaD", simplePipeline.toString).get

      then("the stage details will be correct")
      pipeline.stages(0).name must be === "defaultStage"
      
      and("the job details will be correct")
      pipeline.stages(0).jobs(0).name must be === "defaultJob"
      pipeline.stages(0).jobs(0).status must be === "Failure"
      pipeline.stages(0).jobs(0).timestamp must be  === DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2010-10-09 21:22:16")
    }

    scenario("able to generate a status that can differentiate between failed and not run in this build") {
      given("an xml with a failure mid pipeline")
      val simplePipeline = <Projects>
                             <Project name="vBaD :: firstStage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/pipelines/vBaD/6/defaultStage/1" />
                             <Project name="vBaD :: firstStage :: defaultJob" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/tab/build/detail/vBaD/6/defaultStage/1/defaultJob" />
                             <Project name="vBaD :: secondStage" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/pipelines/vBaD/6/defaultStage/1" />
                             <Project name="vBaD :: secondStage :: defaultJob" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="6" lastBuildTime="2010-10-09T21:22:16" webUrl="http://192.168.1.65:8153/cruise/tab/build/detail/vBaD/6/defaultStage/1/defaultJob" />
                             <Project name="vBaD :: thirdStage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="5" lastBuildTime="2010-10-08T21:22:16" webUrl="http://192.168.1.65:8153/cruise/pipelines/vBaD/5/defaultStage/1" />
                             <Project name="vBaD :: thirdStage :: defaultJob" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="5" lastBuildTime="2010-10-08T21:22:16" webUrl="http://192.168.1.65:8153/cruise/tab/build/detail/vBaD/5/defaultStage/1/defaultJob" />
                           </Projects>

      when("the pipeline is extracted")
      val status = CruisePipeline("vBaD", simplePipeline.toString).get

      then("the correct representation of stages after the failure")
      status.stages(0).status must be === "Success"
      status.stages(1).status must be === "Failure"
      status.stages(2).status must be === "NotBuilt"

    }

  }

}