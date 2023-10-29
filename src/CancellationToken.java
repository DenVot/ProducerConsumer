public class CancellationToken {
  private boolean cancellationRequested = false;

  public boolean isCancellationRequested() {
    return cancellationRequested;
  }

  public void cancel() {
    cancellationRequested = true;
  }
}
