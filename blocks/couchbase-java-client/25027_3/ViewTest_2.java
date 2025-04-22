    }
  }
  
  @Test
  public void testTotalNumRows() {
    Query query = new Query();
    query.setReduce(false).setIncludeDocs(true).setStale(Stale.FALSE);

    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    ViewResponse response = client.query(view, query);
    long totalRows = response.getTotalRows();
    assertEquals(ITEMS.size(), totalRows);

    query.setLimit(5);
    response = client.query(view, query);
    totalRows = response.getTotalRows();
    assertEquals(ITEMS.size(), totalRows);
  }

  @Test(expected = IllegalStateException.class)
  public void shouldRaiseWhenTotalNumRowsOnReducedView() {
    Query query = new Query();
    query.setIncludeDocs(true).setStale(Stale.FALSE);

    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    ViewResponse response = client.query(view, query);
    response.getTotalRows();
