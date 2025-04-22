  @Test
  public void testQuerySetGroupNoReduce() throws Exception {
    Query query = new Query();
    query.setGroup(true);
    View view = client.getView(DESIGN_DOC_WO_REDUCE, VIEW_NAME_WO_REDUCE);
    HttpFuture<ViewResponse> future =
        client.asyncQuery(view, query);
    ViewResponse response = future.get();
    Collection<RowError> errors = response.getErrors();
    assert errors.size() == 1;

    RowError error = errors.iterator().next();
    assert error.getFrom().equals("query_parse_error");
    assert error.getReason().equals("Invalid URL parameter 'group' or  "
        + "'group_level' for non-reduce view.");
    assert response != null : future.getStatus();
  }

