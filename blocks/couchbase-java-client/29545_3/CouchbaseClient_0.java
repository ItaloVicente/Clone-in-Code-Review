    if (toReplica) {
      for (int i = 0; i < cfg.getReplicasCount(); i++) {
        MemcachedNode replica = locator.getReplica(key, i);
        if (replica != null) {
          bcastNodes.add(replica);
        }
