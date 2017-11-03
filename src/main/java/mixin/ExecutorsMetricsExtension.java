package mixin;

public interface ExecutorsMetricsExtension {
    public void incSubmittedTasks();
    public void incCompletedTasksCounter();

    public long submittedTasks();
    public long completedTasksCounter();
}

