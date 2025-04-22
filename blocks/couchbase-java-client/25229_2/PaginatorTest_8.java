
  @Test
  public void testWithExactReduce() {
    View view = client.getView(DESIGN_DOC, VIEW_NAME_MAPRED);
    Query query = new Query();
    query.setGroupLevel(1);
    int docsPerPage = 1;

    Paginator paginatedQuery = client.paginatedQuery(view, query, docsPerPage);

    int pageCount = 0;
    int totalCount = 0;
    while(paginatedQuery.hasNext()) {
      pageCount++;
      ViewResponse response = paginatedQuery.next();
      for(ViewRow row : response) {
        totalCount++;
        assertTrue("Reduce value is 0 or less",
          Integer.parseInt(row.getValue()) > 0);
      }
    }

    assertEquals(3, pageCount);
    assertEquals(3, totalCount);
  }

  @Test
  public void testWithOffsetReduce() {
    View view = client.getView(DESIGN_DOC, VIEW_NAME_MAPRED);
    Query query = new Query();
    query.setGroupLevel(1);
    int docsPerPage = 2;

    Paginator paginatedQuery = client.paginatedQuery(view, query, docsPerPage);

    int pageCount = 0;
    int totalCount = 0;
    while(paginatedQuery.hasNext()) {
      pageCount++;
      ViewResponse response = paginatedQuery.next();
      for(ViewRow row : response) {
        totalCount++;
        assertTrue("Reduce value is 0 or less",
          Integer.parseInt(row.getValue()) > 0);
      }
    }

    assertEquals(2, pageCount);
    assertEquals(3, totalCount);
  }

  @Test
  public void testWithReduceAndLimit() {
    View view = client.getView(DESIGN_DOC, VIEW_NAME_MAPRED);
    Query query = new Query();
    query.setGroupLevel(1);
    query.setLimit(2);
    int docsPerPage = 1;

    Paginator paginatedQuery = client.paginatedQuery(view, query, docsPerPage);

    int pageCount = 0;
    int totalCount = 0;
    while(paginatedQuery.hasNext()) {
      pageCount++;
      ViewResponse response = paginatedQuery.next();
      for(ViewRow row : response) {
        totalCount++;
        assertTrue("Reduce value is 0 or less",
          Integer.parseInt(row.getValue()) > 0);
      }
    }

    assertEquals(2, pageCount);
    assertEquals(2, totalCount);
  }

