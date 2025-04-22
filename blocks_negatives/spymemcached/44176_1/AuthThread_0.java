    Operation listMechsOp = opFact.saslMechs(new OperationCallback() {
      @Override
      public void receivedStatus(OperationStatus status) {
        if(status.isSuccess()) {
          supportedMechs.set(status.getMessage());
          getLogger().debug("Received SASL supported mechs: "
            + status.getMessage());
        } else {
          getLogger().warn("Received non-success response for SASL mechs: "
            + status);
