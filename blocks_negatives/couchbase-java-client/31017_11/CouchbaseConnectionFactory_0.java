  public ViewNode createViewNode(InetSocketAddress addr,
      AsyncConnectionManager connMgr) {
    return new ViewNode(addr, connMgr, opQueueLen,
        getOpQueueMaxBlockTime(), getOperationTimeout(), bucket, pass);
  }

