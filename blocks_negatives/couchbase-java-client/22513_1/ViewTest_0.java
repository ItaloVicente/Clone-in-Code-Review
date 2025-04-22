  @Test
  public void testPaginationItemsModPageSizeNotZero() throws Exception {
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    Query query = new Query();
    query.setReduce(false);
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
    assert count == ITEMS.size() : "Got " + count + " items, wanted "
        + ITEMS.size();
  }

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

  @Test
  public void testPaginationItemsModPageSizeIsZero() throws Exception {
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    Query query = new Query();
    query.setReduce(false);
    Paginator op = client.paginatedQuery(view, query, 10);

    assert client.set("key125", 0,
        generateDoc("a", "b", "c")).get().booleanValue()
        : "Setting key key125 failed";
    assert client.set("key126", 0,
        generateDoc("a", "b", "c")).get().booleanValue()
        : "Setting key key126 failed";
    assert client.set("key127", 0,
        generateDoc("a", "b", "c")).get().booleanValue()
        : "Setting key key127 failed";

    int count = 0;
    while (op.hasNext()) {
      ViewResponse response = op.next();
      for (ViewRow row: response) {
        String key = row.getId();
        if (!ITEMS.containsKey(key)) {
          assert false : "Got bad key: " + key + " during pagination";
        }
        count++;
      }

    }
    assert count == ITEMS.size() : "Got " + count + " items, wanted "
        + ITEMS.size();
    assert client.delete("key125").get().booleanValue()
        : "Deleteing key 125 failed";
    assert client.delete("key126").get().booleanValue()
        : "Deleteing key 125 failed";
    assert client.delete("key127").get().booleanValue()
        : "Deleteing key 125 failed";
    Thread.sleep(1000);
  }

  @Test
  public void testPaginationAndDeleteStartKey() throws Exception {
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    Query query = new Query();
    query.setReduce(false);
    query.setStale(Stale.FALSE);
    Paginator op = client.paginatedQuery(view, query, 10);

    int count = 0;
    while (op.hasNext()) {
      ViewResponse response = op.next();
      for(ViewRow row: response) {
        if (count == 5) {
          assert client.delete("key112").get().booleanValue()
              : "Deleteing key key112 failed";
          Thread.sleep(1000);
        }
        count++;
      }
    }
    assert count == ITEMS.size() - 1 : "Got " + count + " items, wanted "
        + (ITEMS.size() - 1);
    assert client.set("key112", 0,
        generateDoc("a", "b", "c")).get().booleanValue()
        : "Adding key key112 failed";
  }

