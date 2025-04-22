      if (op.isCancelled()) {
        discardedOps++;
        getLogger().debug("Silently discarding replica (active) get for key \""
          + key + "\" (cancelled).");
      } else {
        replicaFuture.addFutureToMonitor(additionalActiveGet);
      }
