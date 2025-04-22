  public void subscribe(String bucketName, Reconfigurable rec) {
    Bucket bucket = getBucketConfiguration(bucketName);

    ReconfigurableObserver obs = new ReconfigurableObserver(rec);
    BucketMonitor monitor = this.monitors.get(bucketName);
    if (monitor == null) {
      URI streamingURI = bucket.getStreamingURI();
      monitor = new BucketMonitor(this.loadedBaseUri.resolve(streamingURI),
        bucketName, this.restUsr, this.restPwd, configurationParser);
      this.monitors.put(bucketName, monitor);
      monitor.addObserver(obs);
      monitor.startMonitor();
    } else {
      monitor.addObserver(obs);
