package extractor

import java.lang.invoke.{MethodHandle, MethodHandles}
import java.util.concurrent.ExecutorService

case class MetricsExtractor(target: ExecutorService) {
  val submittedTasksMethod: MethodHandle = getMethodHandle(target, "getAndResetSubmittedTasks")
  val completedTasksCounterMethod: MethodHandle = getMethodHandle(target, "getAndResetCompletedTasks")

  private def getMethodHandle(target: Any, methodName: String) = {
    val reflectedMethod = target.getClass.getDeclaredMethod(methodName)
    MethodHandles.lookup.unreflect(reflectedMethod)
  }

  def extractSubmittedTasks: Long =
    submittedTasksMethod.invoke(target).asInstanceOf[Long]

  def extractCompletedTasks: Long =
    completedTasksCounterMethod.invoke(target).asInstanceOf[Long]
}

