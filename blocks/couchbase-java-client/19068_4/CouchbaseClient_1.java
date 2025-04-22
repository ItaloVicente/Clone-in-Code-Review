  private void observePoll(String key, long cas,
          PersistTo persist, ReplicateTo replicate) throws TimeoutException {
    int persists, replicates;
    int totPersists, totReplicas;
    boolean masterPersisted;
    int loop = 0;

    int replicas = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplicasCount();

    switch (persist) {
    case MASTER:
      persists=0;
      break;
    case TWO:
      persists=1;
      break;
    case THREE:
      persists=2;
      break;
    case FOUR:
    default:
      persists=3;
    }
    switch (replicate) {
    case ZERO:
      replicates=0;
      break;
    case ONE:
      replicates=1;
      break;
    case TWO:
      replicates=2;
      break;
    case THREE:
    default:
      replicates=3;
    }

    if (replicates > replicas
            || persists > replicas) {
      throw new IllegalArgumentException("Requested Persists and "
              + "Requested Replicates exceed number of replicas =  "
              + replicas);
    }

    ObserveResponse[] or;

    do {
      masterPersisted = false;
      totPersists = totReplicas = 0;

      or = observe(key, cas);
      if ((or[0] != ObserveResponse.FOUND_NOT_PERSISTED)
              && (or[0] != ObserveResponse.NOT_FOUND_NOT_PERSISTED)) {
        masterPersisted = true;
      }
      if ((or[0]) == ObserveResponse.MODIFIED) { // Master
        throw new RuntimeException("Observe - the key was modified");
      }

      for (int i=1; i < or.length; i++) {
        if (or[i] == ObserveResponse.UNINITIALIZED) {
          continue; // Skip to the next entry
        }
        if ((or[i] != ObserveResponse.FOUND_NOT_PERSISTED)
                && or[i] != ObserveResponse.NOT_FOUND_NOT_PERSISTED) {
          totPersists++;
          totReplicas++;
        }
      }
      if (masterPersisted && (totPersists >= persists)
              && (totReplicas >= replicates)) {
        return;
      }
      try {
        Thread.sleep(400);
      } catch (InterruptedException e) {
         getLogger().error("Interrupted while in observe loop.", e);
      }
    } while (loop++ < 10);

    throw new TimeoutException("Observe - Polled for Maximum retries of "
            + loop);
  }

