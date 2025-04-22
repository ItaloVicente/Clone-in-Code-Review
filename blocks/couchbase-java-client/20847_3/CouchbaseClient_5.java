      Map<MemcachedNode, ObserveResponse> response = observe(key, cas);

      int vb = locator.getVBucketIndex(key);
      MemcachedNode master = locator.getServerByIndex(cfg.getMaster(vb));

      replicaPersistedTo = 0;
      replicatedTo = 0;
      persistedMaster = false;
      for (Entry<MemcachedNode, ObserveResponse> r : response.entrySet()) {
        boolean isMaster = r.getKey() == master ? true : false;
        if (isMaster && r.getValue() == ObserveResponse.MODIFIED) {
          throw new ObservedModifiedException("Key was modified");
