  public CouchbaseConnectionFactory buildCouchbaseConnection() throws IOException {
    return new CouchbaseConnectionFactory() {

      @Override
      public BlockingQueue<Operation> createOperationQueue() {
        return opQueueFactory == null ? super.createOperationQueue()
          : opQueueFactory.create();
      }

      @Override
      public BlockingQueue<Operation> createReadOperationQueue() {
        return readQueueFactory == null ? super.createReadOperationQueue()
          : readQueueFactory.create();
      }

      @Override
      public BlockingQueue<Operation> createWriteOperationQueue() {
        return writeQueueFactory == null ? super.createReadOperationQueue()
          : writeQueueFactory.create();
      }

      @Override
      public Transcoder<Object> getDefaultTranscoder() {
        return transcoder == null ? super.getDefaultTranscoder() : transcoder;
      }

      @Override
      public FailureMode getFailureMode() {
        return failureMode == null ? DEFAULT_FAILURE_MODE : failureMode;
      }

      @Override
      public HashAlgorithm getHashAlg() {
        return hashAlg == null ? DEFAULT_HASH : hashAlg;
      }

      @Override
      public Collection<ConnectionObserver> getInitialObservers() {
        return initialObservers;
      }

      @Override
      public OperationFactory getOperationFactory() {
        return opFact == null ? super.getOperationFactory() : opFact;
      }

      @Override
      public long getOperationTimeout() {
        return opTimeout == -1 ? super.getOperationTimeout() : opTimeout;
      }

      @Override
      public int getReadBufSize() {
        return readBufSize == -1 ? super.getReadBufSize() : readBufSize;
      }

      @Override
      public boolean isDaemon() {
        return isDaemon;
      }

      @Override
      public boolean shouldOptimize() {
        return false;
      }

      @Override
      public boolean useNagleAlgorithm() {
        return useNagle;
      }

      @Override
      public long getMaxReconnectDelay() {
        return maxReconnectDelay;
      }

      @Override
      public long getOpQueueMaxBlockTime() {
        return opQueueMaxBlockTime > -1 ? opQueueMaxBlockTime
          : super.getOpQueueMaxBlockTime();
      }

      @Override
      public int getTimeoutExceptionThreshold() {
        return timeoutExceptionThreshold;
      }

      public long getMinReconnectInterval() {
        return reconnThresholdTimeMsecs;
      }

      @Override
      public long getObsPollInterval() {
        return obsPollInterval;
      }

      @Override
      public int getObsPollMax() {
        return obsPollMax;
      }

      @Override
      public int getViewTimeout() {
        return viewTimeout;
      }

    };
  }

