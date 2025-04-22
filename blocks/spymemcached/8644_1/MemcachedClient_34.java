public class MemcachedClient extends SpyObject implements MemcachedClientIF,
    ConnectionObserver {

  protected volatile boolean shuttingDown = false;

  protected final long operationTimeout;

  protected final MemcachedConnection mconn;
  protected final OperationFactory opFact;

  protected final Transcoder<Object> transcoder;

  protected final TranscodeService tcService;

  protected final AuthDescriptor authDescriptor;

  protected final ConnectionFactory connFactory;

  protected final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

  public MemcachedClient(InetSocketAddress... ia) throws IOException {
    this(new DefaultConnectionFactory(), Arrays.asList(ia));
  }

  public MemcachedClient(List<InetSocketAddress> addrs) throws IOException {
    this(new DefaultConnectionFactory(), addrs);
  }

  public MemcachedClient(ConnectionFactory cf, List<InetSocketAddress> addrs)
    throws IOException {
    if (cf == null) {
      throw new NullPointerException("Connection factory required");
    }
    if (addrs == null) {
      throw new NullPointerException("Server list required");
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException(
          "You must have at least one server to connect to");
    }
    if (cf.getOperationTimeout() <= 0) {
      throw new IllegalArgumentException("Operation timeout must be positive.");
    }
    connFactory = cf;
    tcService = new TranscodeService(cf.isDaemon());
    transcoder = cf.getDefaultTranscoder();
    opFact = cf.getOperationFactory();
    assert opFact != null : "Connection factory failed to make op factory";
    mconn = cf.createConnection(addrs);
    assert mconn != null : "Connection factory failed to make a connection";
    operationTimeout = cf.getOperationTimeout();
    authDescriptor = cf.getAuthDescriptor();
    if (authDescriptor != null) {
      addObserver(this);
    }
  }

  public Collection<SocketAddress> getAvailableServers() {
    ArrayList<SocketAddress> rv = new ArrayList<SocketAddress>();
    for (MemcachedNode node : mconn.getLocator().getAll()) {
      if (node.isActive()) {
        rv.add(node.getSocketAddress());
      }
    }
    return rv;
  }

  public Collection<SocketAddress> getUnavailableServers() {
    ArrayList<SocketAddress> rv = new ArrayList<SocketAddress>();
    for (MemcachedNode node : mconn.getLocator().getAll()) {
      if (!node.isActive()) {
        rv.add(node.getSocketAddress());
      }
    }
    return rv;
  }

  public NodeLocator getNodeLocator() {
    return mconn.getLocator().getReadonlyCopy();
  }

  public Transcoder<Object> getTranscoder() {
    return transcoder;
  }

  private void validateKey(String key) {
    byte[] keyBytes = KeyUtil.getKeyBytes(key);
    if (keyBytes.length > MAX_KEY_LENGTH) {
      throw new IllegalArgumentException("Key is too long (maxlen = "
          + MAX_KEY_LENGTH + ")");
    }
    if (keyBytes.length == 0) {
      throw new IllegalArgumentException(
          "Key must contain at least one character.");
    }
    for (byte b : keyBytes) {
      if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
        throw new IllegalArgumentException(
            "Key contains invalid characters:  ``" + key + "''");
      }
    }
  }

  Operation addOp(final String key, final Operation op) {
    validateKey(key);
    mconn.checkState();
    mconn.addOperation(key, op);
    return op;
  }

  CountDownLatch broadcastOp(final BroadcastOpFactory of) {
    return broadcastOp(of, mconn.getLocator().getAll(), true);
  }

  CountDownLatch broadcastOp(final BroadcastOpFactory of,
      Collection<MemcachedNode> nodes) {
    return broadcastOp(of, nodes, true);
  }

  private CountDownLatch broadcastOp(BroadcastOpFactory of,
      Collection<MemcachedNode> nodes, boolean checkShuttingDown) {
    if (checkShuttingDown && shuttingDown) {
      throw new IllegalStateException("Shutting down");
    }
    return mconn.broadcastOperation(of, nodes);
  }

  private <T> OperationFuture<Boolean> asyncStore(StoreType storeType,
      String key, int exp, T value, Transcoder<T> tc) {
    CachedData co = tc.encode(value);
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv =
        new OperationFuture<Boolean>(key, latch, operationTimeout);
    Operation op =
        opFact.store(storeType, key, co.getFlags(), exp, co.getData(),
            new OperationCallback() {
              public void receivedStatus(OperationStatus val) {
                rv.set(val.isSuccess(), val);
              }

              public void complete() {
                latch.countDown();
              }
            });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  private OperationFuture<Boolean> asyncStore(StoreType storeType, String key,
      int exp, Object value) {
    return asyncStore(storeType, key, exp, value, transcoder);
  }

  private <T> OperationFuture<Boolean> asyncCat(ConcatenationType catType,
      long cas, String key, T value, Transcoder<T> tc) {
    CachedData co = tc.encode(value);
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv =
        new OperationFuture<Boolean>(key, latch, operationTimeout);
    Operation op =
        opFact.cat(catType, cas, key, co.getData(), new OperationCallback() {
          public void receivedStatus(OperationStatus val) {
            rv.set(val.isSuccess(), val);
          }

          public void complete() {
            latch.countDown();
          }
        });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public <T> OperationFuture<Boolean> touch(final String key, final int exp) {
    return touch(key, exp, transcoder);
  }

  public <T> OperationFuture<Boolean> touch(final String key, final int exp,
      final Transcoder<T> tc) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv =
        new OperationFuture<Boolean>(key, latch, operationTimeout);

    Operation op = opFact.touch(key, exp, new OperationCallback() {
      public void receivedStatus(OperationStatus status) {
        rv.set(status.isSuccess(), status);
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public OperationFuture<Boolean> append(long cas, String key, Object val) {
    return append(cas, key, val, transcoder);
  }

  public <T> OperationFuture<Boolean> append(long cas, String key, T val,
      Transcoder<T> tc) {
    return asyncCat(ConcatenationType.append, cas, key, val, tc);
  }

  public OperationFuture<Boolean> prepend(long cas, String key, Object val) {
    return prepend(cas, key, val, transcoder);
  }

  public <T> OperationFuture<Boolean> prepend(long cas, String key, T val,
      Transcoder<T> tc) {
    return asyncCat(ConcatenationType.prepend, cas, key, val, tc);
  }

  public <T> Future<CASResponse> asyncCAS(String key, long casId, T value,
      Transcoder<T> tc) {
    return asyncCAS(key, casId, 0, value, tc);
  }

  public <T> Future<CASResponse> asyncCAS(String key, long casId, int exp,
      T value, Transcoder<T> tc) {
    CachedData co = tc.encode(value);
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<CASResponse> rv =
        new OperationFuture<CASResponse>(key, latch, operationTimeout);
    Operation op =
        opFact.cas(StoreType.set, key, casId, co.getFlags(), exp, co.getData(),
            new OperationCallback() {
              public void receivedStatus(OperationStatus val) {
                if (val instanceof CASOperationStatus) {
                  rv.set(((CASOperationStatus) val).getCASResponse(), val);
                } else if (val instanceof CancelledOperationStatus) {
                  getLogger().debug("CAS operation cancelled");
                } else {
                  throw new RuntimeException("Unhandled state: " + val);
                }
              }

              public void complete() {
                latch.countDown();
              }
            });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public Future<CASResponse> asyncCAS(String key, long casId, Object value) {
    return asyncCAS(key, casId, value, transcoder);
  }

  public <T> CASResponse cas(String key, long casId, T value,
      Transcoder<T> tc) {
    return cas(key, casId, 0, value, tc);
  }

  public <T> CASResponse cas(String key, long casId, int exp, T value,
      Transcoder<T> tc) {
    try {
      return asyncCAS(key, casId, exp, value, tc).get(operationTimeout,
          TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public CASResponse cas(String key, long casId, Object value) {
    return cas(key, casId, value, transcoder);
  }

  public <T> OperationFuture<Boolean> add(String key, int exp, T o,
      Transcoder<T> tc) {
    return asyncStore(StoreType.add, key, exp, o, tc);
  }

  public OperationFuture<Boolean> add(String key, int exp, Object o) {
    return asyncStore(StoreType.add, key, exp, o, transcoder);
  }

  public <T> OperationFuture<Boolean> set(String key, int exp, T o,
      Transcoder<T> tc) {
    return asyncStore(StoreType.set, key, exp, o, tc);
  }

  public OperationFuture<Boolean> set(String key, int exp, Object o) {
    return asyncStore(StoreType.set, key, exp, o, transcoder);
  }

  public <T> OperationFuture<Boolean> replace(String key, int exp, T o,
      Transcoder<T> tc) {
    return asyncStore(StoreType.replace, key, exp, o, tc);
  }

  public OperationFuture<Boolean> replace(String key, int exp, Object o) {
    return asyncStore(StoreType.replace, key, exp, o, transcoder);
  }

  public <T> GetFuture<T> asyncGet(final String key, final Transcoder<T> tc) {

    final CountDownLatch latch = new CountDownLatch(1);
    final GetFuture<T> rv = new GetFuture<T>(latch, operationTimeout, key);

    Operation op = opFact.get(key, new GetOperation.Callback() {
      private Future<T> val = null;

      public void receivedStatus(OperationStatus status) {
        rv.set(val, status);
      }

      public void gotData(String k, int flags, byte[] data) {
        assert key.equals(k) : "Wrong key returned";
        val =
            tcService.decode(tc, new CachedData(flags, data, tc.getMaxSize()));
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public GetFuture<Object> asyncGet(final String key) {
    return asyncGet(key, transcoder);
  }

  public <T> OperationFuture<CASValue<T>> asyncGets(final String key,
      final Transcoder<T> tc) {

    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<CASValue<T>> rv =
        new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

    Operation op = opFact.gets(key, new GetsOperation.Callback() {
      private CASValue<T> val = null;

      public void receivedStatus(OperationStatus status) {
        rv.set(val, status);
      }

      public void gotData(String k, int flags, long cas, byte[] data) {
        assert key.equals(k) : "Wrong key returned";
        assert cas > 0 : "CAS was less than zero:  " + cas;
        val =
            new CASValue<T>(cas, tc.decode(new CachedData(flags, data, tc
                .getMaxSize())));
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public OperationFuture<CASValue<Object>> asyncGets(final String key) {
    return asyncGets(key, transcoder);
  }

  public <T> CASValue<T> gets(String key, Transcoder<T> tc) {
    try {
      return asyncGets(key, tc).get(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public <T> CASValue<T> getAndTouch(String key, int exp, Transcoder<T> tc) {
    try {
      return asyncGetAndTouch(key, exp, tc).get(operationTimeout,
          TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public CASValue<Object> getAndTouch(String key, int exp) {
    return getAndTouch(key, exp, transcoder);
  }

  public CASValue<Object> gets(String key) {
    return gets(key, transcoder);
  }

  public <T> T get(String key, Transcoder<T> tc) {
    try {
      return asyncGet(key, tc).get(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for value", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Exception waiting for value", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value", e);
    }
  }

  public Object get(String key) {
    return get(key, transcoder);
  }

  public <T> BulkFuture<Map<String, T>> asyncGetBulk(Collection<String> keys,
      Iterator<Transcoder<T>> tcIter) {
    final Map<String, Future<T>> m = new ConcurrentHashMap<String, Future<T>>();

    final Map<String, Transcoder<T>> tcMap =
        new HashMap<String, Transcoder<T>>();

    final Map<MemcachedNode, Collection<String>> chunks =
        new HashMap<MemcachedNode, Collection<String>>();
    final NodeLocator locator = mconn.getLocator();
    Iterator<String> keyIter = keys.iterator();
    while (keyIter.hasNext() && tcIter.hasNext()) {
      String key = keyIter.next();
      tcMap.put(key, tcIter.next());
      validateKey(key);
      final MemcachedNode primaryNode = locator.getPrimary(key);
      MemcachedNode node = null;
      if (primaryNode.isActive()) {
        node = primaryNode;
      } else {
        for (Iterator<MemcachedNode> i = locator.getSequence(key); node == null
            && i.hasNext();) {
          MemcachedNode n = i.next();
          if (n.isActive()) {
            node = n;
          }
        }
        if (node == null) {
          node = primaryNode;
        }
      }
      assert node != null : "Didn't find a node for " + key;
      Collection<String> ks = chunks.get(node);
      if (ks == null) {
        ks = new ArrayList<String>();
        chunks.put(node, ks);
      }
      ks.add(key);
    }

    final CountDownLatch latch = new CountDownLatch(chunks.size());
    final Collection<Operation> ops = new ArrayList<Operation>(chunks.size());
    final BulkGetFuture<T> rv = new BulkGetFuture<T>(m, ops, latch);

    GetOperation.Callback cb = new GetOperation.Callback() {
      @SuppressWarnings("synthetic-access")
      public void receivedStatus(OperationStatus status) {
        rv.setStatus(status);
        if (!status.isSuccess()) {
          getLogger().warn("Unsuccessful get:  %s", status);
        }
      }

      public void gotData(String k, int flags, byte[] data) {
        Transcoder<T> tc = tcMap.get(k);
        m.put(k,
            tcService.decode(tc, new CachedData(flags, data, tc.getMaxSize())));
      }

      public void complete() {
        latch.countDown();
      }
    };

    final Map<MemcachedNode, Operation> mops =
        new HashMap<MemcachedNode, Operation>();

    for (Map.Entry<MemcachedNode, Collection<String>> me : chunks.entrySet()) {
      Operation op = opFact.get(me.getValue(), cb);
      mops.put(me.getKey(), op);
      ops.add(op);
    }
    assert mops.size() == chunks.size();
    mconn.checkState();
    mconn.addOperations(mops);
    return rv;
  }

  public <T> BulkFuture<Map<String, T>> asyncGetBulk(Collection<String> keys,
      Transcoder<T> tc) {
    return asyncGetBulk(keys, new SingleElementInfiniteIterator<Transcoder<T>>(
        tc));
  }

  public BulkFuture<Map<String, Object>> asyncGetBulk(Collection<String> keys) {
    return asyncGetBulk(keys, transcoder);
  }

  public <T> BulkFuture<Map<String, T>> asyncGetBulk(Transcoder<T> tc,
      String... keys) {
    return asyncGetBulk(Arrays.asList(keys), tc);
  }

  public BulkFuture<Map<String, Object>> asyncGetBulk(String... keys) {
    return asyncGetBulk(Arrays.asList(keys), transcoder);
  }

  public OperationFuture<CASValue<Object>> asyncGetAndTouch(final String key,
      final int exp) {
    return asyncGetAndTouch(key, exp, transcoder);
  }

  public <T> OperationFuture<CASValue<T>> asyncGetAndTouch(final String key,
      final int exp, final Transcoder<T> tc) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<CASValue<T>> rv =
        new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

    Operation op =
        opFact.getAndTouch(key, exp, new GetAndTouchOperation.Callback() {
          private CASValue<T> val = null;

          public void receivedStatus(OperationStatus status) {
            rv.set(val, status);
          }

          public void complete() {
            latch.countDown();
          }

          public void gotData(String k, int flags, long cas, byte[] data) {
            assert k.equals(key) : "Wrong key returned";
            assert cas > 0 : "CAS was less than zero:  " + cas;
            val =
                new CASValue<T>(cas, tc.decode(new CachedData(flags, data, tc
                    .getMaxSize())));
          }
        });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public <T> Map<String, T> getBulk(Collection<String> keys, Transcoder<T> tc) {
    try {
      return asyncGetBulk(keys, tc)
          .get(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted getting bulk values", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed getting bulk values", e);
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for bulkvalues", e);
    }
  }

  public Map<String, Object> getBulk(Collection<String> keys) {
    return getBulk(keys, transcoder);
  }

  public <T> Map<String, T> getBulk(Transcoder<T> tc, String... keys) {
    return getBulk(Arrays.asList(keys), tc);
  }

  public Map<String, Object> getBulk(String... keys) {
    return getBulk(Arrays.asList(keys), transcoder);
  }

  public Map<SocketAddress, String> getVersions() {
    final Map<SocketAddress, String> rv =
        new ConcurrentHashMap<SocketAddress, String>();

    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        final SocketAddress sa = n.getSocketAddress();
        return opFact.version(new OperationCallback() {
          public void receivedStatus(OperationStatus s) {
            rv.put(sa, s.getMessage());
          }

          public void complete() {
            latch.countDown();
          }
        });
      }
    });
    try {
      blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for versions", e);
    }
    return rv;
  }

  public Map<SocketAddress, Map<String, String>> getStats() {
    return getStats(null);
  }

  public Map<SocketAddress, Map<String, String>> getStats(final String arg) {
    final Map<SocketAddress, Map<String, String>> rv =
        new HashMap<SocketAddress, Map<String, String>>();

    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        final SocketAddress sa = n.getSocketAddress();
        rv.put(sa, new HashMap<String, String>());
        return opFact.stats(arg, new StatsOperation.Callback() {
          public void gotStat(String name, String val) {
            rv.get(sa).put(name, val);
          }

          @SuppressWarnings("synthetic-access")
          public void receivedStatus(OperationStatus status) {
            if (!status.isSuccess()) {
              getLogger().warn("Unsuccessful stat fetch: %s", status);
            }
          }

          public void complete() {
            latch.countDown();
          }
        });
      }
    });
    try {
      blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for stats", e);
    }
    return rv;
  }

  private long mutate(Mutator m, String key, int by, long def, int exp) {
    final AtomicLong rv = new AtomicLong();
    final CountDownLatch latch = new CountDownLatch(1);
    addOp(key, opFact.mutate(m, key, by, def, exp, new OperationCallback() {
      public void receivedStatus(OperationStatus s) {
        rv.set(new Long(s.isSuccess() ? s.getMessage() : "-1"));
      }

      public void complete() {
        latch.countDown();
      }
    }));
    try {
      if (!latch.await(operationTimeout, TimeUnit.MILLISECONDS)) {
        throw new OperationTimeoutException(
            "Mutate operation timed out, unable to modify counter [" + key
                + "]");
      }
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted", e);
    }
    getLogger().debug("Mutation returned %s", rv);
    return rv.get();
  }

  public long incr(String key, int by) {
    return mutate(Mutator.incr, key, by, 0, -1);
  }

  public long decr(String key, int by) {
    return mutate(Mutator.decr, key, by, 0, -1);
  }

  public long incr(String key, int by, long def, int exp) {
    return mutateWithDefault(Mutator.incr, key, by, def, exp);
  }

  public long decr(String key, int by, long def, int exp) {
    return mutateWithDefault(Mutator.decr, key, by, def, exp);
  }

  private long mutateWithDefault(Mutator t, String key, int by, long def,
      int exp) {
    long rv = mutate(t, key, by, def, exp);
    if (rv == -1) {
      Future<Boolean> f =
          asyncStore(StoreType.add, key, exp, String.valueOf(def));
      try {
        if (f.get(operationTimeout, TimeUnit.MILLISECONDS)) {
          rv = def;
        } else {
          rv = mutate(t, key, by, 0, exp);
          assert rv != -1 : "Failed to mutate or init value";
        }
      } catch (InterruptedException e) {
        throw new RuntimeException("Interrupted waiting for store", e);
      } catch (ExecutionException e) {
        throw new RuntimeException("Failed waiting for store", e);
      } catch (TimeoutException e) {
        throw new OperationTimeoutException(
            "Timeout waiting to mutate or init value", e);
      }
    }
    return rv;
  }

  private OperationFuture<Long> asyncMutate(Mutator m, String key, int by,
      long def, int exp) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Long> rv =
        new OperationFuture<Long>(key, latch, operationTimeout);
    Operation op =
        addOp(key, opFact.mutate(m, key, by, def, exp, new OperationCallback() {
          public void receivedStatus(OperationStatus s) {
            rv.set(new Long(s.isSuccess() ? s.getMessage() : "-1"), s);
          }

          public void complete() {
            latch.countDown();
          }
        }));
    rv.setOperation(op);
    return rv;
  }

  public OperationFuture<Long> asyncIncr(String key, int by) {
    return asyncMutate(Mutator.incr, key, by, 0, -1);
  }

  public OperationFuture<Long> asyncDecr(String key, int by) {
    return asyncMutate(Mutator.decr, key, by, 0, -1);
  }

  public long incr(String key, int by, long def) {
    return mutateWithDefault(Mutator.incr, key, by, def, 0);
  }

  public long decr(String key, int by, long def) {
    return mutateWithDefault(Mutator.decr, key, by, def, 0);
  }

  @Deprecated
  public OperationFuture<Boolean> delete(String key, int hold) {
    return delete(key);
  }

  public OperationFuture<Boolean> delete(String key) {
    final CountDownLatch latch = new CountDownLatch(1);
    final OperationFuture<Boolean> rv =
        new OperationFuture<Boolean>(key, latch, operationTimeout);
    DeleteOperation op = opFact.delete(key, new OperationCallback() {
      public void receivedStatus(OperationStatus s) {
        rv.set(s.isSuccess(), s);
      }

      public void complete() {
        latch.countDown();
      }
    });
    rv.setOperation(op);
    addOp(key, op);
    return rv;
  }

  public OperationFuture<Boolean> flush(final int delay) {
    final AtomicReference<Boolean> flushResult =
        new AtomicReference<Boolean>(null);
    final ConcurrentLinkedQueue<Operation> ops =
        new ConcurrentLinkedQueue<Operation>();
    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        Operation op = opFact.flush(delay, new OperationCallback() {
          public void receivedStatus(OperationStatus s) {
            flushResult.set(s.isSuccess());
          }

          public void complete() {
            latch.countDown();
          }
        });
        ops.add(op);
        return op;
      }
    });

    return new OperationFuture<Boolean>(null, blatch, flushResult,
        operationTimeout) {
      @Override
      public boolean cancel(boolean ign) {
        boolean rv = false;
        for (Operation op : ops) {
          op.cancel();
          rv |= op.getState() == OperationState.WRITING;
        }
        return rv;
      }

      @Override
      public Boolean get(long duration, TimeUnit units)
        throws InterruptedException, TimeoutException, ExecutionException {
        status = new OperationStatus(true, "OK");
        return super.get(duration, units);
      }

      @Override
      public boolean isCancelled() {
        boolean rv = false;
        for (Operation op : ops) {
          rv |= op.isCancelled();
        }
        return rv;
      }

      @Override
      public boolean isDone() {
        boolean rv = true;
        for (Operation op : ops) {
          rv &= op.getState() == OperationState.COMPLETE;
        }
        return rv || isCancelled();
      }
    };
  }

  public OperationFuture<Boolean> flush() {
    return flush(-1);
  }

  public Set<String> listSaslMechanisms() {
    final ConcurrentMap<String, String> rv =
        new ConcurrentHashMap<String, String>();

    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(MemcachedNode n, final CountDownLatch latch) {
        return opFact.saslMechs(new OperationCallback() {
          public void receivedStatus(OperationStatus status) {
            for (String s : status.getMessage().split(" ")) {
              rv.put(s, s);
            }
          }

          public void complete() {
            latch.countDown();
          }
        });
      }
    });

    try {
      blatch.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    return rv.keySet();
  }

  public void shutdown() {
    shutdown(-1, TimeUnit.MILLISECONDS);
  }

  public boolean shutdown(long timeout, TimeUnit unit) {
    if (shuttingDown) {
      getLogger().info("Suppressing duplicate attempt to shut down");
      return false;
    }
    shuttingDown = true;
    String baseName = mconn.getName();
    mconn.setName(baseName + " - SHUTTING DOWN");
    boolean rv = false;
    try {
      if (timeout > 0) {
        mconn.setName(baseName + " - SHUTTING DOWN (waiting)");
        rv = waitForQueues(timeout, unit);
      }
    } finally {
      try {
        mconn.setName(baseName + " - SHUTTING DOWN (telling client)");
        mconn.shutdown();
        mconn.setName(baseName + " - SHUTTING DOWN (informed client)");
        tcService.shutdown();
      } catch (IOException e) {
        getLogger().warn("exception while shutting down", e);
      }
    }
    return rv;
  }

  public boolean waitForQueues(long timeout, TimeUnit unit) {
    CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
      public Operation newOp(final MemcachedNode n,
          final CountDownLatch latch) {
        return opFact.noop(new OperationCallback() {
          public void complete() {
            latch.countDown();
          }

          public void receivedStatus(OperationStatus s) {
          }
        });
      }
    }, mconn.getLocator().getAll(), false);
    try {
      return blatch.await(timeout, unit);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted waiting for queues", e);
    }
  }

  public boolean addObserver(ConnectionObserver obs) {
    boolean rv = mconn.addObserver(obs);
    if (rv) {
      for (MemcachedNode node : mconn.getLocator().getAll()) {
        if (node.isActive()) {
          obs.connectionEstablished(node.getSocketAddress(), -1);
        }
      }
    }
    return rv;
  }

  public boolean removeObserver(ConnectionObserver obs) {
    return mconn.removeObserver(obs);
  }

  public void connectionEstablished(SocketAddress sa, int reconnectCount) {
    if (authDescriptor != null) {
      if (authDescriptor.authThresholdReached()) {
        this.shutdown();
      }
      authMonitor.authConnection(mconn, opFact, authDescriptor, findNode(sa));
    }
  }

  private MemcachedNode findNode(SocketAddress sa) {
    MemcachedNode node = null;
    for (MemcachedNode n : mconn.getLocator().getAll()) {
      if (n.getSocketAddress().equals(sa)) {
        node = n;
      }
