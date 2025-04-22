
  @Test
  public void testQuerySetRangeStartComplexKey() throws Exception {

    for (int i = 2009; i<2013; i++) {
      for (int j = 1; j<13; j++) {
        for (int k = 1; k<32; k++) {
          client.add("date" + i + j + k, 600, generateDatedDoc(i, j, k));
        }
      }
    }

    Query query = new Query();
    query.setReduce(false);
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    HttpFuture<ViewResponse> future =
        client.asyncQuery(view, query.setRangeStart(ComplexKey.of(2012,9,5)));
    ViewResponse response = future.get();
    assert response != null : future.getStatus();
  }

