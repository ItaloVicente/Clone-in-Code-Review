        FailureMode mode = connectionFactory.getFailureMode();
        if (mode == FailureMode.Redistribute || mode == FailureMode.Retry) {
          getLogger().debug("Redistributing Operation " + op + " because auth "
            + "latch taken longer than " + authWaitTime + " milliseconds to "
            + "complete on node " + getSocketAddress());
          connection.redistributeOperation(op);
        } else {
          op.cancel();
          getLogger().warn("Operation canceled because authentication "
            + "or reconnection and authentication has "
            + "taken more than " + authWaitTime + " milliseconds to "
            + "complete on node " + this);
          getLogger().debug("Canceled operation %s", op.toString());
        }
