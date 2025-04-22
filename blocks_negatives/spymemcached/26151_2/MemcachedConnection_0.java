          final CountDownLatch latch = new CountDownLatch(1);
          final OperationFuture<Boolean> rv =
            new OperationFuture<Boolean>("noop", latch, 2500);
          NoopOperation testOp = opFact.noop(new OperationCallback() {
            public void receivedStatus(OperationStatus status) {
              rv.set(status.isSuccess(), status);
            }
