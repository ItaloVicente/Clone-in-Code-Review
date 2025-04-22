      try {
        if (!conn.isShutDown()) {
            if(!listMechsLatch.await(opTimeout, TimeUnit.MILLISECONDS)) {
              getLogger().warn(host() + " Fetching SASL auth list took longer than "
                + opTimeout + "ms. Retrying.");
            }
        } else {
          done.set(true); // Connection is shutting down, tear.down.
        }
      } catch (InterruptedException ex) {
        getLogger().warn(host() + "Interrupted in Auth while waiting for SASL mechs.");
        Thread.currentThread().interrupt();
        if (listMechsOp != null) {
          listMechsOp.cancel();
        }
        done.set(true); // If we were interrupted, tear down.
