
    MemcachedNode primary;
    if(o instanceof ReplicaGetOperation
      && locator instanceof VBucketNodeLocator) {
      primary = ((VBucketNodeLocator)locator).getReplica(key,
        ((ReplicaGetOperation)o).getReplicaIndex());
    } else {
      primary = locator.getPrimary(key);
    }
