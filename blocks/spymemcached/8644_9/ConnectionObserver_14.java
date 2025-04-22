    return this;
  }

  public Config getVBucketConfig() {
    return vBucketConfig;
  }

  public void setVBucketConfig(Config config) {
    this.vBucketConfig = config;
  }

  public ConnectionFactory build() {
    return new DefaultConnectionFactory() {

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
      public NodeLocator createLocator(List<MemcachedNode> nodes) {
        switch (locator) {
        case ARRAY_MOD:
          return new ArrayModNodeLocator(nodes, getHashAlg());
        case CONSISTENT:
          return new KetamaNodeLocator(nodes, getHashAlg());
        case VBUCKET:
          return new VBucketNodeLocator(nodes, getVBucketConfig());
        default:
          throw new IllegalStateException("Unhandled locator type: " + locator);
        }
      }

      @Override
      public Transcoder<Object> getDefaultTranscoder() {
        return transcoder == null ? super.getDefaultTranscoder() : transcoder;
      }

      @Override
      public FailureMode getFailureMode() {
        return failureMode == null ? super.getFailureMode() : failureMode;
      }

      @Override
      public HashAlgorithm getHashAlg() {
        return hashAlg == null ? super.getHashAlg() : hashAlg;
      }

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
        return shouldOptimize;
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
      public AuthDescriptor getAuthDescriptor() {
        return authDescriptor;
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

    };

  }

  public MembaseConnectionFactory buildMembaseConnection(
      final List<URI> baseList, final String bucketName, final String usr,
      final String pwd) throws IOException {
    return new MembaseConnectionFactory(baseList, bucketName, usr, pwd) {

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
      public NodeLocator createLocator(List<MemcachedNode> nodes) {
        switch (getLocator()) {
        case CONSISTENT:
          return new KetamaNodeLocator(nodes, getHashAlg());
        case VBUCKET:
          return new VBucketNodeLocator(nodes, getVBucketConfig());
        default:
          throw new IllegalStateException("Unhandled locator type: " + locator);
        }
      }

      @Override
      public Transcoder<Object> getDefaultTranscoder() {
        return transcoder == null ? super.getDefaultTranscoder() : transcoder;
      }

      @Override
      public FailureMode getFailureMode() {
        return failureMode;
      }

      @Override
      public HashAlgorithm getHashAlg() {
        return hashAlg;
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
        return shouldOptimize;
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
      public AuthDescriptor getAuthDescriptor() {
        return authDescriptor;
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

    };

  }

  public static enum Protocol {
    TEXT,
    BINARY
  }

  public static enum Locator {
    ARRAY_MOD,
    CONSISTENT,
    VBUCKET
  }
}
