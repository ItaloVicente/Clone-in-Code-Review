    try {
      if (!conn.isShutDown()) {
        listMechsLatch.await();
      } else {
      }
    } catch(InterruptedException ex) {
      getLogger().warn("Interrupted in Auth while waiting for SASL mechs.");
      Thread.currentThread().interrupt();
      if (listMechsOp != null) {
        listMechsOp.cancel();
