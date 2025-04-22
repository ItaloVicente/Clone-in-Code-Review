      if (!authLatch.await(factory.getOperationTimeout(), TimeUnit.MILLISECONDS)) {
        if (factory.getFailureMode() == FailureMode.Redistribute
          || factory.getFailureMode() == FailureMode.Retry) {
          connection.redistributeOperation(op);
        } else {
          op.cancel();
          getLogger().warn("Operation canceled because authentication "
            + "or reconnection and authentication has "
            + "taken more than " + factory.getOperationTimeout()
            + " milliseconds to complete.");
          getLogger().debug("Canceled operation %s", op.toString());
        }
