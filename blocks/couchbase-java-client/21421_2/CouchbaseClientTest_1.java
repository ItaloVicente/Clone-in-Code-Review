
  public void testNullObserve() throws Exception {
    boolean success;
    try {
      success = true;
      OperationFuture<Boolean> nullcheckOp =
        (((CouchbaseClient)client).add("nullcheck", 0, "value", null, null));
      nullcheckOp.get();
      nullcheckOp =
        (((CouchbaseClient)client).set("nullcheck", 0, "value1", null, null));
      nullcheckOp.get();
      nullcheckOp =
        (((CouchbaseClient)client).replace("nullcheck", 0, "value1", null, null));
      nullcheckOp.get();
    } catch(NullPointerException ex) {
      success = false;
    }
    assert success;

  }

