  @Test
  public void testObserveWithStaleFalse()
    throws InterruptedException, ExecutionException {
    int docAmount = 500;
    for (int i = 1; i <= docAmount; i++) {
      String value = "{\"type\":\"observetest\",\"value\":"+i+"}";
      client.set("observetest"+i, 0, value, PersistTo.MASTER, ReplicateTo.ONE);
    }

    Query query = new Query().setStale(Stale.FALSE);
    View view = client.getView(DESIGN_DOC_OBSERVE, VIEW_NAME_OBSERVE);

    HttpFuture<ViewResponse> future = client.asyncQuery(view, query);

    ViewResponse response = future.get();
    assert response != null : future.getStatus();

    Iterator<ViewRow> iterator = response.iterator();
    List<ViewRow> returnedRows = new ArrayList<ViewRow>();
    while (iterator.hasNext()) {
      ViewRow row = iterator.next();
      returnedRows.add(row);
    }

    assertEquals(docAmount, returnedRows.size());
  }

