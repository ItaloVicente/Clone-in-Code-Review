  @Test
  public void shouldIgnoreOutdatedOrCurrentConfig() throws Exception {
    BucketConfigurationProvider provider = new BucketConfigurationProvider(
      seedNodes,
      bucket,
      password,
      new CouchbaseConnectionFactory(seedNodes, bucket, password)
    );

    final AtomicInteger configArrived = new AtomicInteger();
    provider.subscribe(new Reconfigurable() {
      @Override
      public void reconfigure(Bucket bucket) {
        if (new MockUtil().isMock(bucket)) {
          configArrived.incrementAndGet();
        }
      }
    });

    Bucket storedBucket = provider.getConfig();
    assertTrue(storedBucket.getRevision() > 0);


    Bucket oldBucket = mock(Bucket.class);
    when(oldBucket.getName()).thenReturn(bucket);
    when(oldBucket.getRevision()).thenReturn(storedBucket.getRevision() - 1);
    Bucket currentBucket = mock(Bucket.class);
    when(currentBucket.getName()).thenReturn(bucket);
    when(currentBucket.getRevision()).thenReturn(storedBucket.getRevision());
    Bucket newBucket = mock(Bucket.class);
    when(newBucket.getName()).thenReturn(bucket);
    when(newBucket.getRevision()).thenReturn(storedBucket.getRevision() + 1);
    when(newBucket.getConfig()).thenReturn(storedBucket.getConfig());

    provider.setConfig(oldBucket);
    provider.setConfig(currentBucket);
    provider.setConfig(newBucket);

    assertEquals(1, configArrived.get());
  }

  @Test
  public void shouldUseConfigIfRevNotSet() throws Exception {
    BucketConfigurationProvider provider = new BucketConfigurationProvider(
      seedNodes,
      bucket,
      password,
      new CouchbaseConnectionFactory(seedNodes, bucket, password)
    );

    final AtomicInteger configArrived = new AtomicInteger();
    provider.subscribe(new Reconfigurable() {
      @Override
      public void reconfigure(Bucket bucket) {
        if (new MockUtil().isMock(bucket)) {
          configArrived.incrementAndGet();
        }
      }
    });

    Bucket storedBucket = provider.getConfig();
    assertTrue(storedBucket.getRevision() > 0);

    Bucket newBucket = mock(Bucket.class);
    when(newBucket.getName()).thenReturn(bucket);
    when(newBucket.getRevision()).thenReturn(-1L);
    when(newBucket.getConfig()).thenReturn(storedBucket.getConfig());

    provider.setConfig(newBucket);

    assertEquals(1, configArrived.get());
  }

