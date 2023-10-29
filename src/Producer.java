import tasks.ExecutableTask;
import tasks.SleepTask;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer {
  private final ConcurrentLinkedQueue<ExecutableTask> taskQueue;
  private final CancellationToken cancellationToken;
  private final Random random = new Random();
  private final Logger logger = Logger.getLogger(Producer.class.getName());

  private final long WORK_TIME_MILLIS = 30 * 100;
  private final long FREQUENCY_MILLIS = 100;
  private final long NANOS_IN_MILLIS = 1000000;

  public Producer(ConcurrentLinkedQueue<ExecutableTask> taskQueue, CancellationToken cancellationToken) {
    this.taskQueue = taskQueue;
    this.cancellationToken = cancellationToken;
  }

  public void run() {
    try {
      long startTime = System.nanoTime();

      while ((System.nanoTime() - startTime) / NANOS_IN_MILLIS < WORK_TIME_MILLIS) {
        createNewSleepTask();
        Thread.sleep(FREQUENCY_MILLIS);
      }
    } catch (InterruptedException e) {
      logger.log(Level.INFO, "Producer interrupted");
    } finally {
      cancellationToken.cancel();
    }
  }

  private void createNewSleepTask() {
    var task = new SleepTask(Math.abs(random.nextLong(100, 3000)));

    taskQueue.add(task);
  }
}
