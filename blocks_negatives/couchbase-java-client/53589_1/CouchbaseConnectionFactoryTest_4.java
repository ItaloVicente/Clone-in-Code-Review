  /**
   * Make sure that the first calls to pastReconnThreshold() yield false
   * and the first one who is over getMaxConfigCheck() yields true.
   *
   * @throws IOException
   */
  @Test
  public void testPastReconnectThreshold() throws IOException {
    CouchbaseConnectionFactory connFact = buildFactory();

    for(int i=1; i<connFact.getMaxConfigCheck(); i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }

    boolean pastReconnThreshold = connFact.pastReconnThreshold();
    assertTrue(pastReconnThreshold);
  }

  /**
   * Verifies that when
   * {@link com.couchbase.client.CouchbaseConnectionFactory#pastReconnThreshold()}
   * is called in longer frames than the time period allows, no configuration update
   * is triggered.
   */
  @Test
  public void testPastReconnectThresholdWithSleep() throws Exception {
    CouchbaseConnectionFactory connFact = buildFactory();

    for(int i=1; i<=connFact.getMaxConfigCheck()-1; i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }

    Thread.sleep(TimeUnit.SECONDS.toMillis(11));

    for(int i=1; i<=connFact.getMaxConfigCheck()-1; i++) {
      boolean pastReconnThreshold = connFact.pastReconnThreshold();
      assertFalse(pastReconnThreshold);
    }
  }

