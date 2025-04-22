  public void complete() {
    if (--remaining == 0) {
      originalCallback.receivedStatus(mostRecentStatus);
      originalCallback.complete();
    }
  }
