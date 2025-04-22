  private String[] listSupportedSASLMechanisms(AtomicBoolean done) {
    final CountDownLatch listMechsLatch = new CountDownLatch(1);
    final AtomicReference<String> supportedMechs =
      new AtomicReference<String>();
    Operation listMechsOp = opFact.saslMechs(new OperationCallback() {
      @Override
      public void receivedStatus(OperationStatus status) {
        if(status.isSuccess()) {
          supportedMechs.set(status.getMessage());
          getLogger().debug("Received SASL supported mechs: "
            + status.getMessage());
        }
      }

      @Override
      public void complete() {
        listMechsLatch.countDown();
      }

    });

    conn.insertOperation(node, listMechsOp);

    try {
      listMechsLatch.await(10, TimeUnit.SECONDS);
    } catch(InterruptedException ex) {
      Thread.currentThread().interrupt();
      if (listMechsOp != null) {
        listMechsOp.cancel();
      }
      done.set(true); // If we were interrupted, tear down.
    }

    String supported = supportedMechs.get();
    if (supported == null || supported.isEmpty()) {
      throw new IllegalStateException("Got empty SASL auth mech list.");
    }

    return supported.split(MECH_SEPARATOR);
  }

