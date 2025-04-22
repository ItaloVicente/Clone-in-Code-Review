  public <T> GetFuture<T> asyncReplicaGet(final String key,
          final Transcoder<T> tc) {

    final int vb = ((VBucketNodeLocator)
            ((CouchbaseConnection) mconn).getLocator()).getVBucketIndex(key);

    int replicas;

    replicas = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplicasCount();

    List<MemcachedNode> replicaList = new
            ArrayList<MemcachedNode>(replicas);

    VBucketNodeLocator vbNodeLocator =
      ((VBucketNodeLocator)
            ((CouchbaseConnection) mconn).getLocator());

    for (int i=0; i < replicas; i++) {
      int replica = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplica(vb, i);
      if (replica >= 0) { // Replica count is updated, not enough servers
        replicaList.add(vbNodeLocator.getServerByIndex(replica));
      }
    }

    for (MemcachedNode node : replicaList) {
      final CountDownLatch latch = new CountDownLatch(1);
      final GetFuture<T> rv = new GetFuture<T>(latch, operationTimeout, key);
      Operation op = opFact.replica(key, new ReplicaReadOperation.Callback() {

        private Future<T> val = null;

        public void receivedStatus(OperationStatus status) {
          rv.set(val, status);
        }

        public void gotData(int flags, byte[] data) {
          val = tcService.decode(tc,
                  new CachedData(flags, data, tc.getMaxSize()));
        }

        public void complete() {
          latch.countDown();
        }
      });
      rv.setOperation(op);
      ((CouchbaseConnection) mconn).addOperation(key, op, node);
      return rv;
    }
    CountDownLatch latch = new CountDownLatch(0);
    GetFuture<T> rv = new GetFuture<T>(latch, operationTimeout, key);
    rv.set(null, new OperationStatus(false, "Could not read "
            + "from replica(s). Replica Count = " + replicas));
    return rv;
  }

  public GetFuture<Object> asyncReplicaGet(final String key) {
    return asyncReplicaGet(key, transcoder);
  }
