
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

  @Test
  public void testStringifiedNumber() {
    View view = client.getView(DESIGN_DOC, VIEW_NAME_POPULATION_STRING);
    Query query = new Query();
    int docsPerPage = 2;

    Paginator paginatedQuery = client.paginatedQuery(view, query, docsPerPage);
    paginatedQuery.forceKeyType(String.class);
    int pageCount = 0;
    int totalCount = 0;
    while(paginatedQuery.hasNext()) {
      pageCount++;
      ViewResponse response = paginatedQuery.next();
      for(ViewRow row : response) {
        totalCount++;
      }
    }

    int expected = (int)Math.ceil((double)CITY_DOCS.size() / docsPerPage);
    assertEquals(expected, pageCount);
    assertEquals(CITY_DOCS.size(), totalCount);
  }

  @Test
  public void testNumber() {
    View view = client.getView(DESIGN_DOC, VIEW_NAME_POPULATION_INT);
    Query query = new Query();
    int docsPerPage = 2;

    Paginator paginatedQuery = client.paginatedQuery(view, query, docsPerPage);
    int pageCount = 0;
    int totalCount = 0;
    while(paginatedQuery.hasNext()) {
      pageCount++;
      ViewResponse response = paginatedQuery.next();
      for(ViewRow row : response) {
        totalCount++;
      }
    }

    int expected = (int)Math.ceil((double)CITY_DOCS.size() / docsPerPage);
    assertEquals(expected, pageCount);
    assertEquals(CITY_DOCS.size(), totalCount);
  }

