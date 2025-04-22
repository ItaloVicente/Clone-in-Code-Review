  @Override
  protected void initClient() throws Exception {

    initClient(new DefaultConnectionFactory(5, 1024) {
      @Override
      public MemcachedConnection
      createConnection(List<InetSocketAddress> addrs) throws IOException {
        MemcachedConnection rv = super.createConnection(addrs);
        return rv;
      }

      @Override
      public long getOperationTimeout() {
        return 5000;
      }

      @Override
      public BlockingQueue<Operation> createOperationQueue() {
        return new ArrayBlockingQueue<Operation>(getOpQueueLen());
      }

      @Override
      public BlockingQueue<Operation> createReadOperationQueue() {
        return new ArrayBlockingQueue<Operation>((int) (getOpQueueLen() * 1.1));
      }

      @Override
      public BlockingQueue<Operation> createWriteOperationQueue() {
        return createOperationQueue();
      }

      @Override
      public boolean shouldOptimize() {
        return false;
      }

      @Override
      public long getOpQueueMaxBlockTime() {
        return 0;
      }
    });
  }

  private void runOverflowTest(byte[] b) throws Exception {
    Collection<Future<Boolean>> c = new ArrayList<Future<Boolean>>();
    try {
      for (int i = 0; i < 1000; i++) {
        c.add(client.set("k" + i, 0, b));
      }
      fail("Didn't catch an illegal state exception");
    } catch (IllegalStateException e) {
    }
    try {
      Thread.sleep(50);
      for (Future<Boolean> f : c) {
        f.get(1, TimeUnit.SECONDS);
      }
    } catch (TimeoutException e) {
    } catch (ExecutionException e) {
    }
    Thread.sleep(500);
    assertTrue("Was not able to set a key after failure.",
        client.set("kx", 0, "woo").get(10, TimeUnit.SECONDS));
  }

  public void testOverflowingInputQueue() throws Exception {
    runOverflowTest(new byte[] { 1 });
  }

  public void testOverflowingWriteQueue() throws Exception {
    byte[] b = new byte[8192];
    Random r = new Random();
    r.nextBytes(b);
    runOverflowTest(b);
  }

  public void testOverflowingReadQueue() throws Exception {
    byte[] b = new byte[8192];
    Random r = new Random();
    r.nextBytes(b);
    client.set("x", 0, b);

    Collection<Future<Object>> c = new ArrayList<Future<Object>>();
    try {
      for (int i = 0; i < 1000; i++) {
        c.add(client.asyncGet("x"));
      }
      fail("Didn't catch an illegal state exception");
    } catch (IllegalStateException e) {
    }
    try {
      Thread.sleep(50);
      for (Future<Object> f : c) {
        assertTrue(Arrays.equals(b, (byte[]) f.get(5, TimeUnit.SECONDS)));
      }
    } catch (TimeoutException e) {
    } catch (ExecutionException e) {
    }
    Thread.sleep(500);
    assertTrue(client.set("kx", 0, "woo").get(5, TimeUnit.SECONDS));
  }
