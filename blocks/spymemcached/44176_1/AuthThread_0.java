    while (supportedMechs.get() == null || supportedMechs.get().isEmpty()) {
      Operation listMechsOp = opFact.saslMechs(new OperationCallback() {
        @Override
        public void receivedStatus(OperationStatus status) {
          if (status.isSuccess()) {
            supportedMechs.set(status.getMessage());
            getLogger().debug(host() + " Received SASL supported mechs: "
              + status.getMessage());
          } else {
            getLogger().warn(host() + " Received non-success response for SASL mechs: "
              + status);
          }
