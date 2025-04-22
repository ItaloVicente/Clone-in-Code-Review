      assertNull(row.getKey());
      assertEquals(ITEMS.size(), Integer.parseInt(row.getValue()));
    }
  }

  @Test
  public void testImplicitNoReduce() {
    Query query = new Query();
    View view = client.getView(DESIGN_DOC_W_REDUCE, VIEW_NAME_W_REDUCE);
    ViewResponse reduce = client.query(view, query);
    Iterator<ViewRow> iterator = reduce.iterator();
    while(iterator.hasNext()) {
      ViewRow row = iterator.next();
      assertTrue(!row.getKey().isEmpty());
