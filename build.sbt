import NativePackagerKeys._

import com.lihaoyi.workbench.Plugin._

cancelable in Global := true

organization in ThisBuild := "com.github.rotstrom"

val app = crossProject.settings(
  unmanagedSourceDirectories in Compile += baseDirectory.value / "shared" / "main" / "scala",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.4.6",
    "com.lihaoyi" %%% "upickle" % "0.2.7"
  ),
  scalaVersion := "2.11.5"
).jsSettings(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )
).jvmSettings(
  mainClass in(Compile, run) := Some("simple.Server"),
  fork in run := true,
  libraryDependencies ++= Seq(
    "io.spray" %% "spray-can" % "1.3.2",
    "io.spray" %% "spray-routing" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % "2.3.6"
  )
)

lazy val appJS = app.js.settings(
  workbenchSettings,
  updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)
)
lazy val appJVM = app.jvm.settings(
  (resources in Compile) += (fastOptJS in(appJS, Compile)).value.data
)

