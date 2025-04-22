
  @Override
  public OperationFuture<Boolean> flush() {
    return flush(-1);
  }

  @Override
  public OperationFuture<Boolean> flush(final int delay) {

    final CountDownLatch latch = new CountDownLatch(1);
    final FlushRunner flushRunner = new FlushRunner(latch);

    final OperationFuture<Boolean> rv =
            new OperationFuture<Boolean>("", latch, operationTimeout) {

              CouchbaseConnectionFactory factory =
                (CouchbaseConnectionFactory) connFactory;

              @Override
              public boolean cancel() {
                throw new UnsupportedOperationException("Flush cannot be"
                  + " canceled");
              }

              @Override
              public boolean isDone() {
                return flushRunner.status();
              }

              @Override
              public Boolean get(long duration, TimeUnit units) throws
                InterruptedException, TimeoutException,
                ExecutionException {
                if (!latch.await(duration, units)) {
                  throw new TimeoutException("Flush not completed within"
                    + " timeout.");
                }

                return flushRunner.status();
              }

              @Override
              public Boolean get() throws InterruptedException,
                ExecutionException {
                try {
                  return get(factory.getViewTimeout(), TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                  throw new RuntimeException("Timed out waiting for operation",
                    e);
                }
              }

              @Override
              public Long getCas() {
                throw new UnsupportedOperationException("Flush has no CAS"
                  + " value.");
              }

              @Override
              public String getKey() {
                throw new UnsupportedOperationException("Flush has no"
                  + " associated key.");
              }

              @Override
              public OperationStatus getStatus() {
                throw new UnsupportedOperationException("Flush has no"
                  + " OperationStatus.");
              }

              @Override
              public boolean isCancelled() {
                throw new UnsupportedOperationException("Flush cannot be"
                  + " canceled.");
              }


        };

    Thread flusher = new Thread(flushRunner, "Temporary Flusher");
    flusher.setDaemon(true);
    flusher.start();

    return rv;
  }

  private boolean flushBucket() {
    FlushResponse res = cbConnFactory.getClusterManager().flushBucket(
      cbConnFactory.getBucketName());
    return res.equals(FlushResponse.OK);
  }

  private class FlushRunner implements Runnable {

    final CountDownLatch flatch;
    Boolean flushStatus = false;

    public FlushRunner(CountDownLatch latch) {
      flatch = latch;
    }

    public void run() {
      flushStatus = flushBucket();
      flatch.countDown();
    }

    private boolean status() {
      return flushStatus.booleanValue();
    }
  }

