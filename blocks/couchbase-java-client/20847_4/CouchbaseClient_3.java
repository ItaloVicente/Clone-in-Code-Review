    if (replicateTo > replicaCount) {
      throw new ObservedException("Requested replication to " + replicateTo
          + " node(s), but only " + replicaCount + " are avaliable");
    } else if (persistReplica > replicaCount + 1) {
      throw new ObservedException("Requested persistence to " + persistReplica
          + " node(s), but only " + (replicaCount + 1) + " are available.");
