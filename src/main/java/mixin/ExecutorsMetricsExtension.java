package mixin;

public interface ExecutorsMetricsExtension {
    void incSubmittedTasks();
    void incCompletedTasksCounter();

    long getAndResetSubmittedTasks();
    long getAndResetCompletedTasks();
}

