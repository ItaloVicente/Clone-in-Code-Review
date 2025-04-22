  @Override
  public long getOperationTimeout() {
    return operationTimeout;
  }

  @Override
  public MemcachedConnection getConnection() {
    return mconn;
  }

  @Override
  public TranscodeService getTranscoderService() {
    return tcService;
  }

  @Override
  public ExecutorService getExecutorService() {
    return executorService;
  }

