  public ViewNode createViewNode(InetSocketAddress addr,
      AsyncConnectionManager connMgr) {
    return new ViewNode(addr, connMgr,
        new LinkedBlockingQueue<HttpOperation>(opQueueLen),
        getOpQueueMaxBlockTime(), getOperationTimeout());
  }

