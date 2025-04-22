
  public void testStatsKey() throws Exception {
    client.set("key", 0, "value");
    OperationFuture<Map<String, String>> future =
        ((CouchbaseClient)client).getKeyStats("key");
    assertTrue(future.getStatus().isSuccess());
    Map<String, String> stats = future.get();
    assertTrue(stats.size() == 7);
    assertTrue(stats.containsKey("key_vb_state"));
    assertTrue(stats.containsKey("key_flags"));
    assertTrue(stats.containsKey("key_is_dirty"));
    assertTrue(stats.containsKey("key_cas"));
    assertTrue(stats.containsKey("key_data_age"));
    assertTrue(stats.containsKey("key_exptime"));
    assertTrue(stats.containsKey("key_last_modification_time"));

    future = ((CouchbaseClient)client).getKeyStats("key1");
    assertFalse(future.getStatus().isSuccess());
  }

