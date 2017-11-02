package extractor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MetricsExtensionExtractor {
    private final MethodHandle extractSubmittedTasksMethod;
    private final MethodHandle extractCompletedTasksCounterMethod;
    private final Object instance;

    public MetricsExtensionExtractor(Object target) throws IllegalAccessException, NoSuchMethodException {
        final Method reflectMethod1 = getMethod(target, "extractSubmittedTasks");
        final Method reflectMethod2 = getMethod(target, "extractCompletedTasksCounter");

        this.extractSubmittedTasksMethod = getMethodHandle(reflectMethod1);
        this.extractCompletedTasksCounterMethod = getMethodHandle(reflectMethod2);
        this.instance = target;
    }

    private Method getMethod(Object target, String methodName) {
        try {
            return target.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodHandle getMethodHandle(Method reflectMethod) {
        try {
            return MethodHandles.lookup().unreflect(reflectMethod);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Long extractSubmittedTasks() {
        try {
            return (Long) this.extractSubmittedTasksMethod.invoke(instance);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public Long extractCompletedTasksCounter() {
        try {
            return (Long) this.extractCompletedTasksCounterMethod.invoke(instance);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
