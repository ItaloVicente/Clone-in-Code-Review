
      if (oldvBuckets != null) {
        VBucket old = oldvBuckets.get(i);
        if (old.getMaster() == master) {
          boolean identicalReplicas = true;
          for (int r = 0; r < replicaSize; r++) {
            if (replicas[r] != old.getReplica(r)) {
              identicalReplicas = false;
              break;
            }
          }
          if (identicalReplicas) {
            vBuckets.add(old);
            continue;
          }
        }
      }

