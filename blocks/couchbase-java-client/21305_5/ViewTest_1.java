
  @Test
  public void testViewWithBinaryDocs() {
    Date now = new Date();
    client.set("nonjson1", 0, now);
    client.set("nonjson2", 0, 42);

    View view = client.getView(DESIGN_DOC_BINARY, VIEW_NAME_BINARY);
    Query query = new Query();
    query.setIncludeDocs(true);
    query.setReduce(false);
    query.setStale(Stale.FALSE);

    assert view != null : "Could not retrieve view";
    ViewResponse response = client.query(view, query);

    Iterator<ViewRow> itr = response.iterator();
    while (itr.hasNext()) {
      ViewRow row = itr.next();
      if(row.getKey().equals("nonjson1")) {
        assertEquals(now.toString(), row.getDocument().toString());
      }
      if(row.getKey().equals("nonjson2")) {
        assertEquals(42, row.getDocument());
      }

    }

  }
