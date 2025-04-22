
      long timeout = cbConnFactory.getObsTimeout();
      if (req == PersistTo.ZERO && rep == ReplicateTo.ZERO) {
        timeout = operationTimeout;
      }

      casr = casOp.get(timeout, TimeUnit.MILLISECONDS);
