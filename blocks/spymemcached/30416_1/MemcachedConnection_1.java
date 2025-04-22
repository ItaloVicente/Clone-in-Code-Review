    if (verifyAliveOnConnect) {
      final CountDownLatch latch = new CountDownLatch(1);
      final OperationFuture<Boolean> rv = new OperationFuture<Boolean>("noop",
        latch, 2500, listenerExecutorService);
      NoopOperation testOp = opFact.noop(new OperationCallback() {
        public void receivedStatus(OperationStatus status) {
          rv.set(status.isSuccess(), status);
        }

        @Override
        public void complete() {
          latch.countDown();
        }
      });

      testOp.setHandlingNode(node);
      testOp.initialize();
      checkState();
      insertOperation(node, testOp);
      node.copyInputQueue();

      boolean done = false;
      if (sk.isValid()) {
        long timeout = TimeUnit.MILLISECONDS.toNanos(
          connectionFactory.getOperationTimeout());

        long stop = System.nanoTime() + timeout;
        while (stop > System.nanoTime()) {
          handleWrites(node);
          handleReads(node);
          if(done = (latch.getCount() == 0)) {
            break;
          }
        }
      }

      if (!done || testOp.isCancelled() || testOp.hasErrored()
        || testOp.isTimedOut()) {
        throw new ConnectException("Could not send noop upon connect! "
          + "This may indicate a running, but not responding memcached "
          + "instance.");
      }
    }

    connected(node);
    addedQueue.offer(node);
    if (node.getWbuf().hasRemaining()) {
      handleWrites(node);
    }
  }

  private void handleWrites(final MemcachedNode node) throws IOException {
    node.fillWriteBuffer(shouldOptimize);
    boolean canWriteMore = node.getBytesRemainingToWrite() > 0;
