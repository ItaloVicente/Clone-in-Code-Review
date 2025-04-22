  public HttpFuture<SpatialView> asyncGetSpatialView(String designDocumentName,
      final String viewName) {
    designDocumentName = MODE_PREFIX + designDocumentName;
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();
    String uri = "/" + bucket + "/_design/" + designDocumentName;
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<SpatialView> crv = new HttpFuture<SpatialView>(
      couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op =
        new SpatialViewFetcherOperationImpl(request, bucket, designDocumentName,
            viewName, new SpatialViewFetcherOperation.ViewFetcherCallback() {
              private SpatialView view = null;

              @Override
              public void receivedStatus(OperationStatus status) {
                crv.set(view, status);
              }

              @Override
              public void complete() {
                couchLatch.countDown();
              }

              @Override
              public void gotData(SpatialView v) {
                view = v;
              }
            });
    crv.setOperation(op);
    addOp(op);
    assert crv != null : "Problem retrieving spatial view";
    return crv;
  }

