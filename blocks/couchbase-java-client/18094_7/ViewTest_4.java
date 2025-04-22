  @Test(expected = ExecutionException.class)
  public void testQuerySetGroupNoReduce() throws Exception {
    Query query = new Query();
    query.setGroup(true);
    View view = client.getView(DESIGN_DOC_WO_REDUCE, VIEW_NAME_WO_REDUCE);
    client.asyncQuery(view, query).get();
  }

