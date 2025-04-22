  public TestingClient(List<URI> baseList, String bucketName, String pwd)
    throws IOException {
    super(baseList, bucketName, pwd);
  }

  public HttpFuture<String> asyncHttpPut(String uri, String document)
    throws UnsupportedEncodingException {
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<String> crv =
        new HttpFuture<String>(couchLatch, operationTimeout);

    HttpRequest request =
        new BasicHttpEntityEnclosingRequest("PUT", uri, HttpVersion.HTTP_1_1);
    StringEntity entity = new StringEntity(document);
    ((BasicHttpEntityEnclosingRequest) request).setEntity(entity);
    HttpOperationImpl op = new TestOperationImpl(request, new TestCallback() {
      private String json;

      @Override
      public void receivedStatus(OperationStatus status) {
        crv.set(json, status);
      }

      @Override
      public void complete() {
        couchLatch.countDown();
      }

      @Override
      public void getData(String response) {
        json = response;
      }
    });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public HttpFuture<String> asyncHttpGet(String uri)
    throws UnsupportedEncodingException {
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<String> crv =
        new HttpFuture<String>(couchLatch, operationTimeout);

    HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    HttpOperationImpl op = new TestOperationImpl(request, new TestCallback() {
      private String json;

      @Override
      public void receivedStatus(OperationStatus status) {
        crv.set(json, status);
      }

      @Override
      public void complete() {
        couchLatch.countDown();
      }

      @Override
      public void getData(String response) {
        json = response;
      }
    });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public HttpFuture<String> asyncHttpDelete(String uri)
    throws UnsupportedEncodingException {
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<String> crv =
        new HttpFuture<String>(couchLatch, operationTimeout);

    HttpRequest request =
        new BasicHttpRequest("DELETE", uri, HttpVersion.HTTP_1_1);
    HttpOperationImpl op = new TestOperationImpl(request, new TestCallback() {
      private String json;

      @Override
      public void receivedStatus(OperationStatus status) {
        crv.set(json, status);
      }

      @Override
      public void complete() {
        couchLatch.countDown();
      }

      @Override
      public void getData(String response) {
        json = response;
      }
    });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }
