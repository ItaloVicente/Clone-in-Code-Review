      if (!authLatch.await(1, TimeUnit.SECONDS)) {
        op.cancel();
        getLogger().warn("Operation canceled because authentication "
                + "or reconnection and authentication has "
                + "taken more than one second to complete.");
        getLogger().debug("Canceled operation %s", op.toString());
