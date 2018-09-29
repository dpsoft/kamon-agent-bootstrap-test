package run

import scala.util.Random

class Worker {
  def performTask(): Unit = Thread.sleep((Random.nextFloat() * 100) toLong)
}