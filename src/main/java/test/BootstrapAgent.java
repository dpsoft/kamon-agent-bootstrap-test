package test;

import extractor.MetricsExtensionExtractor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BootstrapAgent {
    public static void main(String[] args) throws Throwable {
//       AgentLoader.attachAgentToJVM(KamonAgent.class);
        ExecutorService executor = Executors.newWorkStealingPool();

        for(int i = 0; i < 15; i++) {
            executor.submit(() -> {
                System.out.println("working!!!!!");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        printMetrics(executor);
        executor.shutdown();
    }


    private static void printMetrics(Object target) throws Throwable {
        MetricsExtensionExtractor submittedTaskExtractor = new MetricsExtensionExtractor(target);
        System.out.println("Submitted Tasks => "  + submittedTaskExtractor.extractSubmittedTasks() + " Completed Tasks => "  + submittedTaskExtractor.extractCompletedTasksCounter());
    }
}