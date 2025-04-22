  @Override
  protected void handleWokenUpSelector() {
    long now = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
    long diff = now - lastWrite;
    if (lastWrite > 0 && diff >= ALLOWED_IDLE_TIME) {
      if (outstandingNoops >= 3) {
        cf.getConfigurationProvider().signalOutdated();
        outstandingNoops = 0;
      }


      updateLastWrite();
      getLogger().debug("Wakeup counter triggered, broadcasting noops.");
      final OperationFactory fact = cf.getOperationFactory();
      outstandingNoops++;
      broadcastOperation(new BroadcastOpFactory() {
        @Override
        public Operation newOp(MemcachedNode n, final CountDownLatch latch) {
          return fact.noop(new OperationCallback() {
            @Override
            public void receivedStatus(OperationStatus status) {
              if (status.isSuccess() && outstandingNoops > 0) {
                outstandingNoops--;
              }
            }

            @Override
            public void complete() {}
          });
        }
      });
    }
  }

