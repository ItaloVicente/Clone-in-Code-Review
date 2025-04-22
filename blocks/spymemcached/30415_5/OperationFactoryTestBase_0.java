  public void testNotGrowingCallstack() throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
    GetOperation.Callback cb = new GetOperation.Callback() {
      @Override
      public void receivedStatus(OperationStatus status) {
      }

      @Override
      public void complete() {
        latch.countDown();
      }

      @Override
      public void gotData(String key, int flags, byte[] data) {
      }
    };

    GetOperation operation = ofact.get("key", cb);
    int nestingDepth = 10000000;
    for (int i = 0; i < nestingDepth; i++) {
      List<Operation> clonedOps = (List<Operation>) ofact.clone(operation);
      operation = (GetOperation) clonedOps.get(0);
    }

    operation.getCallback().complete();
    assertTrue(latch.await(1, TimeUnit.SECONDS));
  }

