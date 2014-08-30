name := "long-layover"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava).enablePlugins(SbtWeb)

//Include LESS assets in compile
includeFilter in (Assets, LessKeys.less) := "*.less"
