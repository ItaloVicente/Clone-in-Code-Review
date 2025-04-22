  public void testAddGetSetStatusCodes() throws Exception {
    OperationFuture<Boolean> set = client.set("statusCode1", 0, "value");
    set.get();
    assertEquals(StatusCode.SUCCESS, set.getStatus().getStatusCode());

    GetFuture<Object> get = client.asyncGet("statusCode1");
    get.get();
    assertEquals(StatusCode.SUCCESS, get.getStatus().getStatusCode());

    OperationFuture<Boolean> add = client.add("statusCode1", 0, "value2");
    add.get();
    assertEquals(StatusCode.ERR_NOT_STORED, add.getStatus().getStatusCode());
  }

