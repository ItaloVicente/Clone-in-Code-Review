  public void waitFor() throws InterruptedException {
    if (this.completed) {
      return;
    }
    synchronized (this) {
      while (!this.completed) {
        wait();
      }
    }
  }
