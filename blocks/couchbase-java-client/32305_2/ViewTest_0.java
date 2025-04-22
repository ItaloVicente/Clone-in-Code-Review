  @Test
  public void testViewFutureWithListener() throws Exception {
    final Query query = new Query();
    query.setReduce(false);
    query.setIncludeDocs(true);

    HttpFuture<View> future =
      client.asyncGetView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);

    final CountDownLatch latch = new CountDownLatch(1);
    future.addListener(new HttpCompletionListener() {
      @Override
      public void onComplete(HttpFuture<?> f) throws Exception {
        View view = (View) f.get();
        HttpFuture<ViewResponse> queryFuture = client.asyncQuery(view, query);
        queryFuture.addListener(new HttpCompletionListener() {
          @Override
          public void onComplete(HttpFuture<?> f) throws Exception {
            ViewResponse resp = (ViewResponse) f.get();
            if (resp.size() == ITEMS.size()) {
              latch.countDown();
            }
          }
        });
      }
    });

    assertTrue(latch.await(3, TimeUnit.SECONDS));
  }

