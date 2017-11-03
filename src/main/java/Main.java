import extractor.MetricsExtractor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Throwable {
//       AgentLoader.attachAgentToJVM(KamonAgent.class);
        ExecutorService executor = Executors.newWorkStealingPool();

        //Submit(Runnable)
        for(int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep((long)(Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //Submit(Callable)
        for(int i = 0; i < 100; i++) {
            executor.submit(() -> {
                long millis = (long) (Math.random() * 100);
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return millis;
            });
        }

        extractAndPrintMetrics(executor);
        Thread.sleep(1000);
        extractAndPrintMetrics(executor);
        Thread.sleep(1000);
        extractAndPrintMetrics(executor);
        Thread.sleep(1000);
        extractAndPrintMetrics(executor);
        executor.shutdown();
    }

    private static void extractAndPrintMetrics(ExecutorService target) throws Throwable {
        //We need to extract the information by reflection because we can't cast a ForkJoinPool to an ExecutorsMetricsExtension :(
        MetricsExtractor metricsExtractor = new MetricsExtractor(target);
        System.out.println("Submitted Tasks => "  + metricsExtractor.extractSubmittedTasks() + " Completed Tasks => "  + metricsExtractor.extractCompletedTasks());
    }
}