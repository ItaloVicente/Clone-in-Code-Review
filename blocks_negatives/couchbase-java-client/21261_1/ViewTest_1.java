  @Test
  public void testQuerySetUpdateSeq() throws Exception {
    Query query = new Query();
    query.setReduce(false);
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    HttpFuture<ViewResponse> future =
        client.asyncQuery(view, query.setUpdateSeq(true));
    ViewResponse response = future.get();
    assert response != null : future.getStatus();
  }

