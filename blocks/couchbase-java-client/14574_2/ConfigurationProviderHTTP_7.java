  public void finishResubscribe() {
    monitors.clear();
    subscribe(reSubBucket, reSubRec);
  }

  public void markForResubscribe(String bucketName, Reconfigurable rec) {
    reSubBucket = bucketName; // can't subscribe here, must from user request
    reSubRec = rec;
  }

