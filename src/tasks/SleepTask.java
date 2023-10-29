package tasks;

public class SleepTask implements ExecutableTask {
  private final long sleepTime;

  public SleepTask(long sleepTime) {
    this.sleepTime = sleepTime;
  }

  @Override
  public void run() throws InterruptedException {
    Thread.sleep(sleepTime);
  }
}
