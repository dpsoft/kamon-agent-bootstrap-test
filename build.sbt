/* =========================================================================================
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

name := "Kamon Agent bootstrap"

scalaVersion := "2.12.3"

enablePlugins(JavaAgent)

libraryDependencies ++= Seq(
  "io.kamon"         %% "kamon-core"                     % "1.0.0",
  "io.kamon"         %% "kamon-testkit"                  % "1.0.0",
  "io.kamon"         %% "agent-scala-extension"          % "0.0.8-experimental",
  "ch.qos.logback"   % "logback-classic"                 % "1.2.2"

)

resolvers ++= Seq(
  Resolver.bintrayRepo("kamon-io", "snapshots"),
  Resolver.bintrayRepo("kamon-io", "releases"),
  Resolver.mavenLocal
)

fork in run := true

javaAgents += "io.kamon"    % "kamon-agent"   % "0.0.[version]-experimental"  % "compile;test;runtime"
