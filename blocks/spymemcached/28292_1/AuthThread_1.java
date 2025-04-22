  private AuthType listSupportedSASLMechanisms(AtomicBoolean done) {
    final CountDownLatch listMechsLatch = new CountDownLatch(1);
    final AtomicReference<AuthType> authType = new AtomicReference<AuthType>();
    Operation listMechsOp = opFact.saslMechs(new OperationCallback() {
      @Override
      public void receivedStatus(OperationStatus status) {
        if(status.isSuccess()) {
          authType.set(determineAuthType(status.getMessage()));
          getLogger().debug("Received SASL supported mechs: "
            + status.getMessage());
