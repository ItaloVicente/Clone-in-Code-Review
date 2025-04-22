  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas,
    Object value, PersistTo req, ReplicateTo rep) {
    return asyncCas(key, cas, 0, value, req, rep);
  }

  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas,
    Object value, PersistTo req) {
    return asyncCas(key, cas, value, req, ReplicateTo.ZERO);
  }

  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas,
    Object value, ReplicateTo rep) {
    return asyncCas(key, cas, value, PersistTo.ZERO, rep);
  }

  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, PersistTo req) {
    return asyncCas(key, cas, exp, value, req, ReplicateTo.ZERO);
  }

  @Override
  public OperationFuture<CASResponse> asyncCas(String key, long cas, int exp,
    Object value, ReplicateTo rep) {
    return asyncCas(key, cas, exp, value, PersistTo.ZERO, rep);
  }

  @Override
  public OperationFuture<CASResponse> asyncCas(final String key, long cas,
    int exp, Object value, final PersistTo req, final ReplicateTo rep) {

    if (mconn instanceof CouchbaseMemcachedConnection) {
      throw new IllegalArgumentException("Durability options are not supported"
        + " on memcached type buckets.");
    }

    OperationFuture<CASResponse> casOp = asyncCAS(key, cas, exp, value,
      transcoder);

    final CountDownLatch latch = new CountDownLatch(1);
    final ObserveFuture<CASResponse> observeFuture =
      new ObserveFuture<CASResponse>(key, latch, operationTimeout,
        executorService);

    casOp.addListener(new OperationCompletionListener() {
      @Override
      public void onComplete(OperationFuture<?> future) throws Exception {
        CASResponse casr;

        try {
          casr = (CASResponse) future.get();
          observeFuture.set(casr, future.getStatus());
          observeFuture.setCas(future.getCas());
        } catch (InterruptedException e) {
          casr = CASResponse.EXISTS;
        } catch (ExecutionException e) {
          casr = CASResponse.EXISTS;
        }

        if((casr != CASResponse.OK)
          || (req == PersistTo.ZERO && rep == ReplicateTo.ZERO)) {
          latch.countDown();
          return;
        }

        try {
          observePoll(key, future.getCas(), req, rep, false);
          observeFuture.set(casr, future.getStatus());
        } catch (ObservedException e) {
          observeFuture.set(CASResponse.OBSERVE_ERROR_IN_ARGS,
            new OperationStatus(false, e.getMessage()));
        } catch (ObservedTimeoutException e) {
          observeFuture.set(CASResponse.OBSERVE_TIMEOUT,
            new OperationStatus(false, e.getMessage()));
        } catch (ObservedModifiedException e) {
          observeFuture.set(CASResponse.OBSERVE_MODIFIED,
            new OperationStatus(false, e.getMessage()));
        }

        latch.countDown();
      }
    });

    return observeFuture;
  }

