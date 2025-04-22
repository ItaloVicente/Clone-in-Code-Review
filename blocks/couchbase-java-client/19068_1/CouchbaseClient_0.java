  public OperationFuture<Boolean> set(String key, int exp,
          String value, PersistTo req, ReplicateTo rep) {
    OperationFuture<Boolean> setOp = set(key, exp, value);
    try {
      if (setOp.get()) {
        observePoll(key, setOp.getCas(), req, rep);
      }
    } catch (InterruptedException e) {
      setOp.set(false, setOp.getStatus());
    } catch (ExecutionException e) {
      setOp.set(false, setOp.getStatus());
    } catch (TimeoutException e) {
      setOp.set(false, setOp.getStatus());
    } catch (IllegalArgumentException e) {
      setOp.set(false, setOp.getStatus());
    } catch (RuntimeException e) {
      setOp.set(false, setOp.getStatus());
    }
    return (setOp);
  }
  public OperationFuture<Boolean> set(String key, int exp,
          String value, PersistTo req) {
    return set(key, exp, value, req, ReplicateTo.ZERO);
  }
  public ObserveResponse[] observe(final String key, final long cas) {
    final ObserveResponse[] observeResponse = new
            ObserveResponse[VBucket.MAX_REPLICAS];
    for (int i=0; i < VBucket.MAX_REPLICAS; i++) {
      observeResponse[i] = ObserveResponse.UNINITIALIZED;
    }
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<ObserveResponse> rv =
        new OperationFuture<ObserveResponse>(key, latch, operationTimeout);
    final AtomicReference<ObserveResponse> observeResult =
        new AtomicReference<ObserveResponse>(null);
    final ConcurrentLinkedQueue<Operation> ops =
        new ConcurrentLinkedQueue<Operation>();
    final int vb = ((VBucketNodeLocator)
            ((CouchbaseConnection) mconn).getLocator()).getVBucketIndex(key);
    int vbucket = vb;
    final int master, replicas;

    master = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getMaster(vbucket);
    replicas = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplicasCount();

    Collection allNodes = (mconn.getLocator()).getAll();

    List<MemcachedNode> replicaList = new
            ArrayList<MemcachedNode>(allNodes.size());
    List<MemcachedNode> masterList = new
            ArrayList<MemcachedNode>(1);

    masterList.add((MemcachedNode) allNodes.toArray()[master]);

    for (int i=0; i < replicas; i++) {
      int replica = ((CouchbaseConnectionFactory)
            connFactory).getVBucketConfig().getReplica(vbucket, i);
      if (i > 0) { // Replica count is updated, not enough servers
        replicaList.add((MemcachedNode) allNodes.toArray()[replica]);
      }

    }

    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        final SocketAddress sa = n.getSocketAddress();
        return opFact.observe(key, cas, vb, new ObserveOperation.Callback() {
          private ObserveResponse r = null;

          public void receivedStatus(OperationStatus s) {
          }

          public void gotData(String key, long retCas,
                  ObserveResponse or) {
            observeResponse[0] = or;
            if (retCas != cas) {
              observeResponse[0] = ObserveResponse.MODIFIED;
            }
            r = observeResponse[0];
          }
          public void complete() {
            latch.countDown();
          }
        });
      }
    }, masterList);
    try {
      blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    }


    blatch = broadcastOp(new BroadcastOpFactory() {

      public Operation newOp(final MemcachedNode n,
              final CountDownLatch latch) {
        final SocketAddress sa = n.getSocketAddress();
        return opFact.observe(key, cas, vb, new ObserveOperation.Callback() {

          private ObserveResponse r = null;

          public void receivedStatus(OperationStatus s) {
          }

          public void gotData(String key, long retCas,
                  ObserveResponse or) {
            r = or;
            for (int i = 1; i <= replicas; i++) {
              if (observeResponse[i] == ObserveResponse.UNINITIALIZED) {
                r = observeResponse[i] = or;
                if (retCas != cas) {
                  r = observeResponse[i] = ObserveResponse.MODIFIED;
                }
              }
            }

          }

          public void complete() {
            latch.countDown();
          }
        });
      }
    }, replicaList);
    try {
      blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
      return observeResponse;
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    }
  }

