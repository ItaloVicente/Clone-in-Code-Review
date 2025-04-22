
      if (op.isCancelled()) {
        discardedOps++;
        getLogger().debug("Silently discarding replica get for key \""
          + key + "\" (cancelled).");
      } else {
        replicaFuture.addFutureToMonitor(rv);
      }

    }

    GetFuture<T> additionalActiveGet = asyncGet(key, tc);
    if (additionalActiveGet.isCancelled()) {
      discardedOps++;
      getLogger().debug("Silently discarding replica (active) get for key \""
        + key + "\" (cancelled).");
    } else {
      replicaFuture.addFutureToMonitor(additionalActiveGet);
    }

    if (discardedOps == replicaCount + 1) {
      throw new IllegalStateException("No replica get operation could be "
        + "dispatched because all operations have been cancelled.");
