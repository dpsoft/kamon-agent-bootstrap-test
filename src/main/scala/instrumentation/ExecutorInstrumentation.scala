package instrumentation

import java.{lang, util}
import java.util.concurrent.Callable

import advisor.Advisors
import kamon.Kamon
import kanela.agent.bootstrap.metrics.{MetricsHandler, MetricsProvider}
import kanela.agent.scala.KanelaInstrumentation

class ExecutorInstrumentation extends KanelaInstrumentation {

  MetricsHandler.setMetricsProvider(new KamonExecutorMetrics)

  forSubtypeOf("java.util.concurrent.Executor") { builder =>
    builder
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Runnable])), classOf[Advisors.SubmitRunnableParameterWrapper])
      .withAdvisorFor(method("submit").and(takesArguments(classOf[Callable[_]])), classOf[Advisors.SubmitCallableParameterWrapper])
      .build()
  }
}

final class KamonExecutorMetrics extends MetricsProvider {
  def incrementCounter(name: String, tags: util.Map[String, String]):Unit = {
    import scala.collection.JavaConverters._
    Kamon.counter(name).refine(tags.asScala.toMap).increment()
  }

  override def incrementCounter(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def incrementGauge(name: String, tags: util.Map[String, String]): Unit = ???

  override def incrementGauge(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def decrementGauge(name: String, tags: util.Map[String, String]): Unit = ???

  override def decrementGauge(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def setGauge(name: String, value: lang.Long): Unit = ???

  override def recordHistogram(name: String, tags: util.Map[String, String]): Unit = ???

  override def recordHistogram(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def incrementRangeSampler(name: String, tags: util.Map[String, String]): Unit = ???

  override def incrementRangeSampler(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def decrementRangeSampler(name: String, tags: util.Map[String, String]): Unit = ???

  override def decrementRangeSampler(name: String, times: lang.Long, tags: util.Map[String, String]): Unit = ???

  override def sampleRangeSampler(): Unit = ???
}

