package interceptor

import kamon.agent.scala.KamonInstrumentation
import mixin.KamonMetricsExtension

class ForkJoinPoolInstrumentation extends KamonInstrumentation {
  forTargetType("java.util.concurrent.ForkJoinPool") { builder =>
    builder
      .withMixin(() => classOf[KamonMetricsExtension])
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Runnable])), classOf[Advisors.SubmitParameterWrapper])
      .build()
  }
}

