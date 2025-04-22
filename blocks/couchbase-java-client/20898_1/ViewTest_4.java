  @Test
  public void testPaginationItemsLimit() throws Exception {
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    Query query = new Query();
    final int limit = 0x45;
    query.setReduce(false);
    query.setLimit(limit);
    Paginator op = client.paginatedQuery(view, query, 10);

    int count = 0;
    while (op.hasNext()) {
      ViewResponse response = op.next();
      for (ViewRow row: response) {
        if (!ITEMS.containsKey(row.getId())) {
          assert false : "Got bad key: " + row.getId() + " during pagination";
        }
        count++;
      }
    }
    assert count == limit : "Got " + count + " items, wanted "
        + limit;
  }

