import tasks.ExecutableTask;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var consumersCount = scanner.nextInt();
        var consumersThreadPool = Executors.newFixedThreadPool(consumersCount);

        var taskQueue = new ConcurrentLinkedQueue<ExecutableTask>();
        var cancellationToken = new CancellationToken();
        var producer = new Producer(taskQueue, cancellationToken);

        for (int i = 0; i < consumersCount; i++) {
            consumersThreadPool.submit(new Worker(cancellationToken, taskQueue));
        }

        producer.run();
        consumersThreadPool.shutdown();
    }
}