//package net.usersource.vbad.lib
//
//import org.specs.runner.{JUnit, Runner}
//import org.specs.Specification
//
//class CruiseBuildStatusSpec extends Runner(CruiseBuildStatus1Spec) with JUnit
//
//object CruiseBuildStatus1Spec extends Specification {
//
//  val data = <Projects>
//               <Project name="PipelineA :: Stage1" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/BuildAndUnitTest/1" />
//               <Project name="PipelineA :: Stage1 :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/BuildAndUnitTest/1/build" />
//               <Project name="PipelineA :: Stage2" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/IntegrationTest/1" />
//               <Project name="PipelineA :: Stage2 :: integration" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/IntegrationTest/1/IntegrationTest" />
//               <Project name="PipelineA :: Stage3" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/CodeCoverage/1" />
//               <Project name="PipelineA :: Stage3 :: coverage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/CodeCoverage/1/sonar" />
//               <Project name="PipelineA :: Stage4" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/Deploy/1" />
//               <Project name="PipelineA :: Stage4 :: deploy" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/Deploy/1/deploy-artifacts" />
//               <Project name="PipelineA :: Stage5" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/SeleniumTest/1" />
//               <Project name="PipelineA :: Stage5 :: accept" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/SeleniumTest/1/SeleniumTest" />
//               <Project name="PipelineA :: Stage6" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/106/PerformanceTest/1" />
//               <Project name="PipelineA :: Stage6 :: performance" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/106/PerformanceTest/1/PT" />
//               <Project name="PipelineB :: Stage1" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusTestTool/6/BuildUnitTest/1" />
//               <Project name="PipelineB :: Stage1 :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusTestTool/6/BuildUnitTest/1/build" />
//             </Projects>
//
//  "CruiseBuildStatus be constructed from xml" should {
//
//    val pipeline = CruiseBuildFactory.buildPipeline("PipelineA", data.toString)
//
//    "have correct number of elements" in {
//      println(pipeline)
//      pipeline.length mustBe 12
//    }
//
//    "have two stage one" in {
//      pipeline.filter( status => status.name.contains("Stage1") ).length mustBe 2
//    }
//  }
//
////  "foo" should {
////    "not be bar" in {
////      true must beFalse
////    }
////  }
///*
//  val data = <Projects>
//               <Project name="PipelineA :: Stage1" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/BuildAndUnitTest/1" />
//               <Project name="PipelineA :: Stage1 :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:43:47" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/BuildAndUnitTest/1/build" />
//               <Project name="PipelineA :: Stage2" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/IntegrationTest/1" />
//               <Project name="PipelineA :: Stage2 :: integration" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:44:25" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/IntegrationTest/1/IntegrationTest" />
//               <Project name="PipelineA :: Stage3" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/CodeCoverage/1" />
//               <Project name="PipelineA :: Stage3 :: coverage" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:50:24" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/CodeCoverage/1/sonar" />
//               <Project name="PipelineA :: Stage4" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/Deploy/1" />
//               <Project name="PipelineA :: Stage4 :: deploy" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="113" lastBuildTime="2010-08-17T18:52:48" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/Deploy/1/deploy-artifacts" />
//               <Project name="PipelineA :: Stage5" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/113/SeleniumTest/1" />
//               <Project name="PipelineA :: Stage5 :: accept" activity="Sleeping" lastBuildStatus="Failure" lastBuildLabel="113" lastBuildTime="2010-08-17T18:55:37" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/113/SeleniumTest/1/SeleniumTest" />
//               <Project name="PipelineA :: Stage6" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusSystem/106/PerformanceTest/1" />
//               <Project name="PipelineA :: Stage6 :: performance" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="106" lastBuildTime="2010-08-16T14:08:39" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusSystem/106/PerformanceTest/1/PT" />
//               <Project name="PipelineB :: Stage1" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/stage/detail/BonusTestTool/6/BuildUnitTest/1" />
//               <Project name="PipelineB :: Stage1 :: build" activity="Sleeping" lastBuildStatus="Success" lastBuildLabel="6" lastBuildTime="2010-08-17T19:03:35" webUrl="http://10.23.30.42:8153/cruise/tab/build/detail/BonusTestTool/6/BuildUnitTest/1/build" />
//             </Projects>
//
//
//  "CruiseBuildStatus be constructed from xml" should {
//
//      val status = CruiseBuildFactory.build("PipelineA", data.toString)
//
//      "have a valid status" in {
//        status.isDefined mustBe true
//      }
//
//      "have the right build name" in {
//        status.get.name mustBe "PipelineA"
//      }
//
//      "have the right build status" in {
//        status.get.status mustBe "Failure"
//      }
//
//      "have the right timestamp" in {
//        status.get.timestamp mustBe "2010-08-17T18:55:37"
//      }
//
//
//  }
//
//*/
//
//}