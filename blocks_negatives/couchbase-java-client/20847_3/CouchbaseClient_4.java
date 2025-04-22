    int replicas = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplicasCount();

    switch (persist) {
    case MASTER:
    case ONE:
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
