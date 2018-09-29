package advisor;

import kanela.agent.bootstrap.metrics.MetricsHandler;
import kanela.agent.libs.net.bytebuddy.asm.Advice;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class Advisors {
    public static class SubmitRunnableParameterWrapper {

        @Advice.OnMethodEnter
        public static void wrapParam(@Advice.This Executor executor, @Advice.Argument(value = 0, readOnly = false) Runnable runnable) {
            MetricsHandler.incrementCounter("executor.submitted-task", Collections.singletonMap("tpe", "fjp"));
            runnable = SubmitRunnableParameterWrapper.wrap(runnable, executor);
        }

        public static Runnable wrap(Runnable runnable, Executor executor) {
            return () -> {
                try {
                    runnable.run();
                } finally {
                    MetricsHandler.incrementCounter("executor.completed-task", Collections.singletonMap("tpe", "fjp"));
                }
            };
        }
    }


    public static class SubmitCallableParameterWrapper {

        @Advice.OnMethodEnter
        public static void wrapParam(@Advice.This Executor executor, @Advice.Argument(value = 0, readOnly = false) Callable<?> callable) {
            MetricsHandler.incrementCounter("executor.submitted-task", Collections.singletonMap("tpe", "fjp"));
            callable = SubmitCallableParameterWrapper.wrap(callable, executor);
        }

        public static Callable<?> wrap(Callable callable, Executor executor) {
            return (Callable<Object>) () -> {
                try {
                    return callable.call();
                } finally {
                    MetricsHandler.incrementCounter("executor.completed-task", Collections.singletonMap("tpe", "fjp"));
                }
            };
        }
    }
}