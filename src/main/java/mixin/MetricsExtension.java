package mixin;

public interface MetricsExtension {
    public void incSubmittedTasks();
    public void incCompletedTasksCounter();

    public Long extractSubmittedTasks();
    public Long extractCompletedTasksCounter();
}

