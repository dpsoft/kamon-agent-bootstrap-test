package mixin;

import kamon.agent.api.instrumentation.mixin.Initializer;

import java.util.concurrent.atomic.AtomicLong;

public class KamonMetricsExtension implements MetricsExtension {
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
    public Long extractSubmittedTasks() {
        return submittedTasksCounter.getAndSet(0);
    }

    @Override
    public Long extractCompletedTasksCounter() {
        return completedTasksCounter.getAndSet(0);
    }
}
