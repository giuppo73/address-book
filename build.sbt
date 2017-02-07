name := "address-book"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xfatal-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import"
)

mainClass in Compile := Some("com.address.book.Main")

// Test
val specs2Version = "3.8.6"
libraryDependencies ++= Seq(
  "org.specs2"                    %% "specs2-core"                % specs2Version  % "test",
  "org.specs2"                    %% "specs2-mock"                % specs2Version  % "test")
