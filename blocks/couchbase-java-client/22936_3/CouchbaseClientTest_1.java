  public void testObservePersistWithTooFewNodes() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    OperationFuture<Boolean> invalid = cb.set("something", 0,
      "to_store", PersistTo.FOUR);
    assertFalse(invalid.get());
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the persistence constraint.";
    assertEquals(expected, invalid.getStatus().getMessage());
  }

  public void testObserveReplicateWithTooFewNodes() throws Exception {
    CouchbaseClient cb = (CouchbaseClient) client;
    OperationFuture<Boolean> invalid = cb.set("something", 0,
      "to_store", ReplicateTo.THREE);
    assertFalse(invalid.get());
    String expected = "Currently, there are less nodes in the cluster than "
      + "required to satisfy the replication constraint.";
    assertEquals(expected, invalid.getStatus().getMessage());
  }

