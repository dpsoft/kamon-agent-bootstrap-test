package run

import java.util.concurrent.{Callable, ExecutorService, Executors}

import extractor.MetricsExtractor

object Main extends App {
  // AgentLoader.attachAgentToJVM(KamonAgent.class);
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
    //We need to extract the information by reflection because we can't cast a ForkJoinPool to an ExecutorsMetricsExtension :(
    val metricsExtractor = MetricsExtractor(target)
    println(s"Submitted Tasks => ${metricsExtractor.extractSubmittedTasks} Completed Tasks => ${metricsExtractor.extractCompletedTasks}")
  }
}
