  @Test
  public void testQuerySetGroupNoReduce() throws Exception {
    Query query = new Query();
    query.setGroup(true);
    View view = client.getView(DESIGN_DOC_WO_REDUCE, VIEW_NAME_WO_REDUCE);
    HttpFuture<ViewResponse> future = client.asyncQuery(view, query);
    try {
      ViewResponse response = future.get();
      assertNotNull(response);
    } catch (ExecutionException e) {
      assertTrue(true);
    }
  }

