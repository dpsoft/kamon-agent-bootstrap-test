package mixin;

import kamon.agent.api.instrumentation.mixin.Initializer;

import java.util.concurrent.atomic.AtomicLong;

public class KamonExecutorsMetricsExtension implements ExecutorsMetricsExtension {
    private AtomicLong submittedTasksCounter;
    private AtomicLong completedTasksCounter;

    @Initializer
    public void initialize() {
        this.submittedTasksCounter = new AtomicLong();
        this.completedTasksCounter = new AtomicLong();
    }

    @Override
    public void incSubmittedTasks() {
        submittedTasksCounter.incrementAndGet();
    }

    @Override
    public void incCompletedTasksCounter() {
         completedTasksCounter.incrementAndGet();
    }

    @Override
    public long getAndResetSubmittedTasks() {
        return submittedTasksCounter.getAndSet(0);
    }

    @Override
    public long getAndResetCompletedTasks() {
        return completedTasksCounter.getAndSet(0);
    }
}
