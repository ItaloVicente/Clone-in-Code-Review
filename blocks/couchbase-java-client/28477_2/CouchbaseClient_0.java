    MemcachedNode primary = locator.getPrimary(key);
    if (primary != null) {
      bcastNodes.add(primary);
    }

    for (int i = 0; i < cfg.getReplicasCount(); i++) {
      MemcachedNode replica = locator.getReplica(key, i);
      if (replica != null) {
        bcastNodes.add(replica);
