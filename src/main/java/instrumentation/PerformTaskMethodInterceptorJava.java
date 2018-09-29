package instrumentation;

import kanela.agent.libs.net.bytebuddy.implementation.bind.annotation.Origin;
import kanela.agent.libs.net.bytebuddy.implementation.bind.annotation.RuntimeType;
import kanela.agent.libs.net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;

public class PerformTaskMethodInterceptorJava {
    @RuntimeType
    public Object around(@SuperCall Callable<?> callable, @Origin String origin) throws Exception {
        final long start = System.nanoTime();
        try {
            return callable.call();
        } finally {
            final float timeSpent = (float) System.nanoTime() - start;
            System.out.println(String.format("Method %s was executed in %10.2f ns.", origin, timeSpent));
        }
    }
}