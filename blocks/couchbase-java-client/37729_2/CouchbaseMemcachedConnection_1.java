  private void updateLastWrite() {
    lastWrite = System.nanoTime();
  }


  @Override
  protected void handleWokenUpSelector() {
    long diff = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - lastWrite);
    if (lastWrite > 0 && diff >= ALLOWED_IDLE_TIME) {
      updateLastWrite();
      getLogger().debug("Wakeup counter triggered, broadcasting noops.");
      final OperationFactory fact = cf.getOperationFactory();
      broadcastOperation(new BroadcastOpFactory() {
        @Override
        public Operation newOp(MemcachedNode n, final CountDownLatch latch) {
          return fact.noop(new OperationCallback() {
            @Override
            public void receivedStatus(OperationStatus status) { }

            @Override
            public void complete() {
              latch.countDown();
            }
          });
        }
      });
    }
  }

