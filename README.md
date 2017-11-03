# Bootstrap Injection with Kamon Agent

This example shows how we can instrument subclasses of `java.util.concurrent.ExecutorService` like `ForkJoinPool` using the almighty Kamon Agent ;).

## Run with `-javaagent`
```text
sbt run
```
## Attach in Runtime
1 - uncomment in  `Main.scala`
```text
  AgentLoader.attachAgentToJVM(KamonAgent.class);
```
2 - comment in `build.sbt`
```text
  javaAgents += "io.kamon"    % "kamon-agent"   % "0.0.9-experimental"  % "compile;test;runtime"
```
3 - `sbt run`

Enjoy!!
