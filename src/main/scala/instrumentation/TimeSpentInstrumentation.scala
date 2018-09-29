package instrumentation

import java.util.concurrent.Callable

import kanela.agent.api.instrumentation.mixin.Initializer
import kanela.agent.libs.net.bytebuddy.asm.Advice._
import kanela.agent.libs.net.bytebuddy.implementation.bind.annotation
import kanela.agent.libs.net.bytebuddy.implementation.bind.annotation.{RuntimeType, SuperCall}
import kanela.agent.scala.KanelaInstrumentation

import scala.collection.mutable.ListBuffer

class TimeSpentInstrumentation extends KanelaInstrumentation {
  forTargetType("run.Worker") { builder ⇒
    builder
      .withMixin(classOf[MonitorAwareMixin])
      .withAdvisorFor(method("performTask"), classOf[PerformTaskMethodAdvisor])
//      .withInterceptorFor(method("performTask"), PerformTaskMethodInterceptor)
      .build()
  }
}

class PerformTaskMethodAdvisor
object PerformTaskMethodAdvisor {

  @OnMethodEnter
  def onMethodEnter(): Long =
    System.nanoTime() // Return current time, entering as parameter in the onMethodExit

  @OnMethodExit
//  def onMethodExit(@This instance: MonitorAware, @Enter start: Long, @Origin origin: String): Unit = {
  def onMethodExit(@This instance: MonitorAware, @Enter start: Long): Unit = {
    val timeSpent = System.nanoTime() - start
    instance.addTimeSpent(timeSpent)
  }
}

object PerformTaskMethodInterceptor {

  @RuntimeType
  def around(@SuperCall callable: Callable[_], @annotation.Origin origin:String): Any = {
    val start = System.nanoTime()
    try callable.call() finally {
      println(s"Method $origin was executed in ${System.nanoTime() - start} ns.")
    }
  }
}

trait MonitorAware {
  def timeSpentList: ListBuffer[Long]
  def addTimeSpent(time:Long)
}

class MonitorAwareMixin extends MonitorAware {
  private var _timeSpentList:ListBuffer[Long] = _

  @Initializer
  def initialize(): Unit =
    this._timeSpentList = ListBuffer.empty[Long]

  def timeSpentList: ListBuffer[Long] =
    this._timeSpentList

  def addTimeSpent(time: Long): Unit =
    _timeSpentList.append(time)
}