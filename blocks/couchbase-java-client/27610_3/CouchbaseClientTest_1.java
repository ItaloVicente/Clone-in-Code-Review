  public void testNoPersistRepObserve() throws Exception {
    OperationFuture<Boolean> noPersistRep =
      (((CouchbaseClient)client).add("testNoPersistRepObserve", 0, "value",
      PersistTo.ZERO, ReplicateTo.ZERO));
    assertTrue("Key not added and not persisted to master : "
      + noPersistRep.getStatus().getMessage(), noPersistRep.get());
  }

  public void testStaleCAS() throws Exception {
    OperationFuture<Boolean> staleCasOp =
      (((CouchbaseClient)client).add("testStaleCAS", 0, "value"));
    long cas1 = staleCasOp.getCas();
    client.set("testStaleCAS", 0, "value2").getCas();
    assertFalse(client.append(cas1, "testStaleCAS", "")
      .getStatus().isSuccess());
    assertEquals((client.append(cas1, "testStaleCAS", "")
      .getStatus().getMessage()),"Data exists for key");
  }
