      replicaPersistedTo = 0;
      replicatedTo = 0;
      persistedMaster = false;
      for (Entry<MemcachedNode, ObserveResponse> r : response.entrySet()) {
        boolean isMaster = r.getKey() == master ? true : false;
        System.out.println("* " + r.getValue() + " Master: " + isMaster);
        if (isMaster && r.getValue() == ObserveResponse.MODIFIED) {
          throw new ObservedModifiedException("Key was modified");
