      if(getCb != null) {
        rv.add(get(k, getCb));
      } else if(getsCb != null) {
        rv.add(get(k, getCb));
      } else {
        rv.add(replicaGet(k, ((ReplicaGetOperationImpl)op).getReplicaIndex() ,replicaGetCb));
      }
