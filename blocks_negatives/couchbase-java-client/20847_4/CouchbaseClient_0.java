    master = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getMaster(vb);
    replicas = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplicasCount();

    List<MemcachedNode> masterList = new
            ArrayList<MemcachedNode>(1);
    List<MemcachedNode> replicaList = new
            ArrayList<MemcachedNode>(replicas);

    VBucketNodeLocator vbNodeLocator =
      ((VBucketNodeLocator)
            ((CouchbaseConnection) mconn).getLocator());

    masterList.add((MemcachedNode)
            vbNodeLocator.getServerByIndex(master));

    for (int i=0; i < replicas; i++) {
      int replica = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplica(vb, i);
        replicaList.add(vbNodeLocator.getServerByIndex(replica));
      }

    }
