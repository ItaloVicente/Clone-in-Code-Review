
          final CountDownLatch latch = new CountDownLatch(1);
          final OperationFuture<Boolean> rv =
            new OperationFuture<Boolean>("", latch, 2500);  //TODO: follow default op timeout
          NoopOperation testOp = opFact.noop(new OperationCallback() {
            public void receivedStatus(OperationStatus status) {
              rv.set(status.isSuccess(), status);
            }

            public void complete() {
              latch.countDown();
            }
          });
          qa.addOp(testOp);
          handleReadsAndWrites(sk, qa); // read first, then write, so twice
          handleReadsAndWrites(sk, qa);
          boolean done = latch.await(2500, TimeUnit.MILLISECONDS);

