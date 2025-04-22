
      @Override
      public void complete() {
        latch.countDown();
      }
    };

    final Operation op = buildOperation(priorStatus, cb);
    conn.insertOperation(node, op);

    try {
      latch.await();
      Thread.sleep(100);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      if (op != null) {
        op.cancel();
      }
      done.set(true); // If we were interrupted, tear down.
    }

    priorStatus = foundStatus.get();
    if (priorStatus != null) {
      if (!priorStatus.isSuccess()) {
        getLogger().warn(
            "Authentication failed to " + node.getSocketAddress());
      }
    }
  }

  private void doCramMD5Auth(AtomicBoolean done) {
    System.out.println("now its time for cram md5");
    done.set(true);
  }

  @Override
  public void run() {
    final AtomicBoolean done = new AtomicBoolean();

    AuthType chosenMechanism = listSupportedSASLMechanisms(done);

    while (!done.get()) {
      if(chosenMechanism == AuthType.PLAIN) {
        OperationStatus priorStatus = null;
        doPlainAuth(done, priorStatus);
      } else if(chosenMechanism == AuthType.CRAM_MD5) {
        doCramMD5Auth(done);
      } else {
        throw new IllegalStateException("Unhandled SASL Auth mechanism found: "
          + chosenMechanism);
      }
