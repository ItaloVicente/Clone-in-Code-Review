
          final CountDownLatch latch = new CountDownLatch(1);
          final OperationFuture<Boolean> rv =
            new OperationFuture<Boolean>("noop", latch, 2500);
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
          node.insertOp(testOp);
          boolean done = latch.await(connectionFactory.getOperationTimeout(),
            TimeUnit.MILLISECONDS);
          if (!done || testOp.isCancelled() || testOp.hasErrored() ||
            testOp.isTimedOut()) {
            throw new ConnectException("Could not send noop upon connect");
          }
          
          connected(node);
          addedQueue.offer(node);
          if (node.getWbuf().hasRemaining()) {
            handleWrites(sk, node);
