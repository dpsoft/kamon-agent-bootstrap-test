package mixin;

public interface ExecutorsMetricsExtension {
    public void incSubmittedTasks();
    public void incCompletedTasksCounter();

    public long getAndResetSubmittedTasks();
    public long getAndResetCompletedTasks();
}

