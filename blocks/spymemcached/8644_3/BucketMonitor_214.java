  }

  public String getHttpUser() {
    return httpUser;
  }

  public String getHttpPass() {
    return httpPass;
  }

  private void logFiner(String msg) {
    Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINER, msg);
  }

  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public void shutdown(long timeout, TimeUnit unit) {
    deleteObservers();
    if (channel != null) {
      channel.close().awaitUninterruptibly(timeout, unit);
