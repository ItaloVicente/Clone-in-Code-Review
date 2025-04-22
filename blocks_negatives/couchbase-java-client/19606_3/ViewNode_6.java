    return !writeQ.isEmpty();
  }

  public void addOp(HttpOperation op) {
    try {
      if (!writeQ.offer(op, opQueueMaxBlockTime, TimeUnit.MILLISECONDS)) {
        throw new IllegalStateException("Timed out waiting to add " + op
            + "(max wait=" + opQueueMaxBlockTime + "ms)");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Interrupted while waiting to add " + op);
    }
