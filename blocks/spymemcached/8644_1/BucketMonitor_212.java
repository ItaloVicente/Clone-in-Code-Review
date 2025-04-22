    this.cometStreamURI = cometStreamURI;
    this.httpUser = username;
    this.httpPass = password;
    this.configParser = configParser;
    this.host = cometStreamURI.getHost();
    this.port = cometStreamURI.getPort() == -1 ? 80 : cometStreamURI.getPort();
    factory =
        new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool());
  }

  public void startMonitor() {
    if (channel != null) {
      Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
          "Bucket monitor is already started.");
      return;
