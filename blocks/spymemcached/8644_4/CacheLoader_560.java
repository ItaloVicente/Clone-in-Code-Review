  private final ExecutorService executorService;
  private final StorageListener storageListener;
  private final MemcachedClientIF client;
  private final int expiration;

  public CacheLoader(MemcachedClientIF c) {
    this(c, null, null, 0);
  }

  public CacheLoader(MemcachedClientIF c, ExecutorService es,
      StorageListener sl, int exp) {
    super();
    client = c;
    executorService = es;
    storageListener = sl;
    expiration = exp;
  }

  public <T> Future<?> loadData(Iterator<Map.Entry<String, T>> i) {
    Future<Boolean> mostRecent = null;
    while (i.hasNext()) {
      Map.Entry<String, T> e = i.next();
      mostRecent = push(e.getKey(), e.getValue());
      watch(e.getKey(), mostRecent);
    }

    return mostRecent == null ? new ImmediateFuture(true) : mostRecent;
  }

  public <T> Future<?> loadData(Map<String, T> map) {
    return loadData(map.entrySet().iterator());
  }

  public <T> Future<Boolean> push(String k, T value) {
    Future<Boolean> rv = null;
    while (rv == null) {
      try {
        rv = client.set(k, expiration, value);
      } catch (IllegalStateException ex) {
        try {
          if (rv != null) {
            rv.get(250, TimeUnit.MILLISECONDS);
          } else {
            Thread.sleep(250);
          }
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        } catch (Exception e2) {
        }
      }

    }
    return rv;
  }

  private void watch(final String key, final Future<Boolean> f) {
    if (executorService != null && storageListener != null) {
      executorService.execute(new Runnable() {
        public void run() {
          try {
            storageListener.storeResult(key, f.get());
          } catch (Exception e) {
            storageListener.errorStoring(key, e);
          }
        }
      });
    }
  }

  public interface StorageListener {

    void storeResult(String k, boolean result);

    void errorStoring(String k, Exception e);
  }
