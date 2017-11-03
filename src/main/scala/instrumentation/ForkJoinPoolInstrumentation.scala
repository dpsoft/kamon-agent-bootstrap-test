package instrumentation

import java.util.concurrent.Callable

import advisor.Advisors
import kamon.agent.scala.KamonInstrumentation
import mixin.KamonExecutorsMetricsExtension

class ForkJoinPoolInstrumentation extends KamonInstrumentation {
  forSubtypeOf("java.util.concurrent.ExecutorService") { builder =>
    builder
      .withMixin(classOf[KamonExecutorsMetricsExtension])
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Runnable])), classOf[Advisors.SubmitRunnableParameterWrapper])
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Callable[_]])), classOf[Advisors.SubmitCallableParameterWrapper])
      .build()
  }
}

