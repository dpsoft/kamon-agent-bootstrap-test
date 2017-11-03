package advisor;

import kamon.agent.libs.net.bytebuddy.asm.Advice;
import mixin.ExecutorsMetricsExtension;

import java.util.concurrent.Callable;

public class Advisors {
    public static class SubmitRunnableParameterWrapper {
        @Advice.OnMethodEnter
        public static void wrapParam(@Advice.This ExecutorsMetricsExtension extension, @Advice.Argument(value = 0, readOnly = false) Runnable runnable) {
            extension.incSubmittedTasks();
            runnable = SubmitRunnableParameterWrapper.wrap(runnable, extension);
        }

        public static Runnable wrap(Runnable runnable, ExecutorsMetricsExtension extension) {
            return () -> {
                try {
                    runnable.run();
                } finally {
                    extension.incCompletedTasksCounter();
                }
            };
        }
    }


    public static class SubmitCallableParameterWrapper {
        @Advice.OnMethodEnter
        public static void wrapParam(@Advice.This ExecutorsMetricsExtension extension, @Advice.Argument(value = 0, readOnly = false) Callable<?> callable) {
            extension.incSubmittedTasks();
            callable = SubmitCallableParameterWrapper.wrap(callable, extension);
        }

        public static Callable<?> wrap(Callable callable, ExecutorsMetricsExtension extension) {
            return (Callable<Object>) () -> {
                try {
                    return callable.call();
                }finally {
                    extension.incCompletedTasksCounter();
                }
            };
        }
    }
}