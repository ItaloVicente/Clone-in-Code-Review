    this.host = cometStreamURI.getHost();
    this.port = cometStreamURI.getPort() == -1 ? 80 : cometStreamURI.getPort();
    factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool());
    this.headers = new HttpMessageHeaders();
      this.provider = provider;
