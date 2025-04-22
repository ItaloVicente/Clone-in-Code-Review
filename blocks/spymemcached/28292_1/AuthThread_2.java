  private void doPlainAuth(final AtomicBoolean done,
    OperationStatus priorStatus) {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<OperationStatus> foundStatus =
      new AtomicReference<OperationStatus>();

    final OperationCallback cb = new OperationCallback() {

      @Override
      public void receivedStatus(OperationStatus val) {
        if (val.getMessage().length() == 0) {
          done.set(true);
          node.authComplete();
          getLogger().info("Authenticated to " + node.getSocketAddress());
        } else {
          foundStatus.set(val);
