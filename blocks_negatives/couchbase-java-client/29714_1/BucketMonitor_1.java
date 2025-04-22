  /**
   * Update the config if it has changed and notify our observers.
   *
   * @param bucketToMonitor the bucketToMonitor to set
   */
  private void setBucket(Bucket newBucket) {
    if (this.bucket == null || !this.bucket.equals(newBucket)) {
      this.bucket = newBucket;
      setChanged();
      notifyObservers(this.bucket);
    }
  }

