      if (op.isCancelled()) {
        discardedOps++;
        getLogger().info("Silently discarding replica get for key " + key);
      } else {
        futures.add(rv);
      }

    }

    GetFuture<T> additionalActiveGet = asyncGet(key, tc);
    if (additionalActiveGet.isCancelled()) {
      discardedOps++;
      getLogger().info("Silently discarding replica (active) get for key "
        + key);
    } else {
      futures.add(additionalActiveGet);
    }

    if (discardedOps == replicaCount + 1) {
      throw new IllegalStateException("No replica get operation could be "
        + "dispatched because all operations have been discarded before.");
