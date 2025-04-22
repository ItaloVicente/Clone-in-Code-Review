  public void testAsyncIncrementWithDefault() throws Exception {
    String k = "async-incr-with-default";
    try {
      client.asyncIncr(k, 1, 5);
      assertTrue(false);
    } catch (UnsupportedOperationException e) {
      assertTrue(true);
    }
  }

  public void testAsyncDecrementWithDefault() throws Exception {
    String k = "async-decr-with-default";
    try {
      client.asyncDecr(k, 1, 5);
      assertTrue(false);
    } catch (UnsupportedOperationException e) {
      assertTrue(true);
    }
  }

