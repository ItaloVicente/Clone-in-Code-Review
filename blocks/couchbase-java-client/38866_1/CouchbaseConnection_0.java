      primary = ((VBucketNodeLocator) locator).getReplica(key,
        ((ReplicaGetOperation) o).getReplicaIndex());
    } else if(o instanceof ReplicaGetsOperation
      && locator instanceof VBucketNodeLocator) {
      primary = ((VBucketNodeLocator) locator).getReplica(key,
        ((ReplicaGetsOperation) o).getReplicaIndex());
