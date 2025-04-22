
          final CountDownLatch latch = new CountDownLatch(1);
          final OperationFuture<Boolean> rv =
            new OperationFuture<Boolean>("", latch, 2500);  //TODO: follow default op timeout
          NoopOperation testOp = opFact.noop(new OperationCallback() {
            public void receivedStatus(OperationStatus status) {
              rv.set(status.isSuccess(), status);
            }

            @Override
            public void complete() {
              latch.countDown();
            }
          });

          qa.addOp(testOp);
          boolean done = latch.await(connectionFactory.getOperationTimeout(), TimeUnit.MILLISECONDS);
          if (!done || testOp.isCancelled() || testOp.hasErrored() || testOp.isTimedOut()) {
            throw new ConnectException("Could not send noop upon connect");
          }
          
