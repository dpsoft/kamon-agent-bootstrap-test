package interceptor;

import mixin.MetricsExtension;
import kamon.agent.libs.net.bytebuddy.asm.Advice;

public class Advisors {
    public static class SubmitParameterWrapper {
        @Advice.OnMethodEnter
        public static void wrapParam(@Advice.This MetricsExtension extension, @Advice.Argument(value = 0, readOnly = false) Runnable runnable) {
            extension.incSubmittedTasks();
            runnable = SubmitParameterWrapper.wrap(runnable, extension);
        }

        public static Runnable wrap(Runnable runnable, MetricsExtension extension) {
            return () -> {
                try {
                    runnable.run();
                }finally {
                    extension.incCompletedTasksCounter();
                }
            };
        }
    }
}