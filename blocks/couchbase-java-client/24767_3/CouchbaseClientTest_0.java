   public void testPersistZeroNodes() throws Exception {
     CouchbaseClient cb = (CouchbaseClient) client;
     OperationFuture<Boolean> success = cb.set("something", 0,
       "to_store", PersistTo.ZERO);
     assertTrue(success.get());
     String expected = "OK";
     assertEquals(expected, success.getStatus().getMessage());
     assertNotNull(cb.get("something"));
   }

  public void testReplicateZeroNodes() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    OperationFuture<Boolean> success = cb.set("something", 0,
      "to_store", ReplicateTo.ZERO);
    assertTrue(success.get());
    String expected = "OK";
    assertEquals(expected, success.getStatus().getMessage());
    assertNotNull(cb.get("something"));
  }

  public void testPersistToMoreThanConf() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    int size = client.getAvailableServers().size();
    OperationFuture<Boolean> future = null;
    switch(size){
    case 0:
    future = cb.set("something", 0,
      "to_store", PersistTo.ONE);
    assertFalse(future.get());
    break;
    case 1:
    future = cb.set("something", 0,
      "to_store", PersistTo.TWO);
    assertFalse(future.get());
    break;
    case 2:
    future = cb.set("something", 0,
      "to_store", PersistTo.THREE);
    assertFalse(future.get());
    break;
    case 3:
    future = cb.set("something", 0,
      "to_store", PersistTo.FOUR);
    assertFalse(future.get());
    break;
    }
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the persistence constraint.";
    assertEquals(expected, future.getStatus().getMessage());
  }

  public void testReplicateToMoreThanConf() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    int size = client.getAvailableServers().size();
    OperationFuture<Boolean> future = null;
    switch(size){
    case 0:
    future = cb.set("something", 0,
      "to_store", ReplicateTo.ONE);
    assertFalse(future.get());
    break;
    case 1:
    future = cb.set("something", 0,
      "to_store", ReplicateTo.TWO);
    assertFalse(future.get());
    break;
    case 2:
    future = cb.set("something", 0,
      "to_store", ReplicateTo.THREE);
    assertFalse(future.get());
    break;
    }
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the replication constraint.";
    assertEquals(expected, future.getStatus().getMessage());
  }

  public void testObsDeleteKeyExists() throws Exception {
    client.set("observetest", 0, "something");
    OperationFuture<Boolean> delOp =
            (((CouchbaseClient)client).delete("observetest",
                PersistTo.MASTER));
    assert delOp.get();
    assertNotNull(delOp.getStatus().getMessage());
  }

