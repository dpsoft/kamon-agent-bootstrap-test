package run

import java.util.concurrent.{Callable, ExecutorService, Executors}

import kamon.Kamon
import kamon.attach.AgentLoader
import kamon.metric.{Counter, CounterMetric, LongAdderCounter}
//import kanela.agent.Kanela
import kanela.agent.attacher.Kanela

object Main extends App {
//  AgentLoader.attachAgentToJVM(classOf[Kanela])
  Kanela.attach()
  val executor: ExecutorService = Executors.newWorkStealingPool()

  //Submit(Runnable)
  for (_ <- 1 to 100) yield {
    executor.submit(new Runnable {
      override def run(): Unit = {
        Thread.sleep((Math.random() * 100).toLong)
      }
    })
  }

  //Submit(Callable)
  for (_ <- 1 to 100) {
    executor.submit(new Callable[Long] {
      override def call(): Long = {
        val millis = (Math.random() * 100).toLong
        Thread.sleep(millis)
        millis
      }
    })
  }

  extractAndPrintMetrics(executor)
  Thread.sleep(1000)
  extractAndPrintMetrics(executor)
  Thread.sleep(1000)
  extractAndPrintMetrics(executor)
  Thread.sleep(1000)
  extractAndPrintMetrics(executor)

  executor.shutdown()

  def extractAndPrintMetrics(target: ExecutorService): Unit = {
    import Metrics._

    val submittedTasks = Kamon.counter("executor.submitted-task").refine(Map("tpe" -> "fjp")).value()
    val completedTasks = Kamon.counter("executor.completed-task").refine(Map("tpe" -> "fjp")).value()

    println(s"Submitted Tasks => $submittedTasks Completed Tasks => $completedTasks")
  }
}

object Metrics {
  implicit class CounterMetricSyntax(counter: Counter) {
    def value(resetState: Boolean = true): Long =
      counter match {
        case cm: CounterMetric    => cm.refine(Map.empty[String, String]).value(resetState)
        case c: LongAdderCounter  => c.snapshot(resetState).value
      }
  }
}