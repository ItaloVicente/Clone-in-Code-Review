
  public long lastReadsdDelta() {
    return System.currentTimeMillis() - lastReadTimestamp;
  }

  public void completedRead() {
    lastReadTimestamp = System.currentTimeMillis();
  }

