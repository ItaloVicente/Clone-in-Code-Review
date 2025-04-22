
  public long lastReadDelta() {
    return System.currentTimeMillis() - lastReadTimestamp;
  }

  public void completedRead() {
    lastReadTimestamp = System.currentTimeMillis();
  }

