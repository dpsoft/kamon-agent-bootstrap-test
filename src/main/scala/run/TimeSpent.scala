package run

import instrumentation.MonitorAware

object TimeSpent extends App {
  val worker = new Worker()
  for(_ <- 1 to 10) {
    worker.performTask()
  }
  print(s"The recorded values for Worker::performTask are ${worker.asInstanceOf[MonitorAware].timeSpentList.mkString(", ")}")
}
