package instrumentation

import java.util
import java.util.concurrent.Callable

import advisor.Advisors
import kamon.Kamon
import kamon.agent.bootstrap.metrics.{Metrics, MetricsHandler}
import kamon.agent.scala.KamonInstrumentation

class ExecutorInstrumentation extends KamonInstrumentation {

  MetricsHandler.setMetricImplementation(new KamonExecutorMetrics)

  forSubtypeOf("java.util.concurrent.Executor") { builder =>
    builder
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Runnable])), classOf[Advisors.SubmitRunnableParameterWrapper])
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Callable[_]])), classOf[Advisors.SubmitCallableParameterWrapper])
      .build()
  }
}

final class KamonExecutorMetrics extends Metrics {
  def incrementCounter(name: String, tags: util.Map[String, String]):Unit = {
    import scala.collection.JavaConverters._
    Kamon.counter(name).refine(tags.asScala.toMap).increment()
  }
}

