        if (!isDelete) {
          if (r.getValue() == ObserveResponse.FOUND_NOT_PERSISTED) {
            replicatedTo++;
          }
          if (r.getValue() == ObserveResponse.FOUND_PERSISTED) {
            replicatedTo++;
            if (isMaster) {
              persistedMaster = true;
            } else {
              replicaPersistedTo++;
            }
          }
        } else {
          if (r.getValue() == ObserveResponse.NOT_FOUND_NOT_PERSISTED) {
            replicatedTo++;
          }
          if (r.getValue() == ObserveResponse.NOT_FOUND_PERSISTED) {
            replicatedTo++;
            replicaPersistedTo++;
            if (isMaster) {
              persistedMaster = true;
            } else {
              replicaPersistedTo++;
            }
          }
