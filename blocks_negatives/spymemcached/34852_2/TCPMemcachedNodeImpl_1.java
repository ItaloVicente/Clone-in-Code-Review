        op.cancel();
        getLogger().warn("Operation canceled because authentication "
          + "or reconnection and authentication has "
          + "taken more than " + authWaitTime + " milliseconds to "
          + "complete.");
        getLogger().debug("Canceled operation %s", op.toString());
