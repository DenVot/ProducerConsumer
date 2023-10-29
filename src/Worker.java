import tasks.ExecutableTask;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker implements Runnable {
  private final CancellationToken cancellationToken;
  private final ConcurrentLinkedQueue<ExecutableTask> tasksQueue;
  private static final Logger logger = Logger.getLogger(Worker.class.getName());


  public Worker(CancellationToken cancellationToken, ConcurrentLinkedQueue<ExecutableTask> tasksQueue) {

    this.cancellationToken = cancellationToken;
    this.tasksQueue = tasksQueue;
  }

  @Override
  public void run() {
    while (!cancellationToken.isCancellationRequested()) {
      if (tasksQueue.isEmpty()) continue;;

      var task = tasksQueue.poll();

      logger.log(Level.INFO, "New task received");

      try {
        task.run();
      } catch (InterruptedException e) {
        logger.log(Level.INFO, "Task interrupted");
      }
    }
  }
}
