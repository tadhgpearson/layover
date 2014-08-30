name := "long-layover"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)
