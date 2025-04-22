    MemcachedNode primary;
    if(o instanceof ReplicaGetOperation) {
      primary = locator.getReplica(key);
    } else {
      primary = locator.getPrimary(key);
    }

