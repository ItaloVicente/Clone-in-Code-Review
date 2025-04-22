
  public void testObserve() throws Exception {
    assertNull(client.get("observetest"));
    OperationFuture<Boolean> setOp =
            (((CouchbaseClient)client).set("observetest", 0, "value",
                PersistTo.MASTER));
    assert setOp.get().booleanValue()
            : "Key was not persisted to master";
    setOp = (((CouchbaseClient)client).set("observetest", 0, "value",
            PersistTo.FOUR, ReplicateTo.THREE));
    assert !setOp.get().booleanValue()
            : "Was there really 4 servers with 3 replicas"
            + "for a testing system?";
  }
