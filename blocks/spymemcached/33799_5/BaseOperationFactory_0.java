    } else if (op instanceof ReplicaGetsOperation) {
      ReplicaGetsOperation.Callback callback =
        (ReplicaGetsOperation.Callback) op.getCallback();
      for (String k : op.getKeys()) {
        rv.add(replicaGets(k,
          ((ReplicaGetsOperation) op).getReplicaIndex(), callback));
      }
