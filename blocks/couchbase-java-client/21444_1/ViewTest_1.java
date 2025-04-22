
  @Test
  public void testObserveWithNoStale()
    throws InterruptedException, ExecutionException {
    int docAmount = 100;
    for(int i=1;i<=docAmount;i++) {
      String value = "{type:\"observetest\",value:"+i+"}";
      client.set("observetest"+i, 0, value, PersistTo.MASTER, ReplicateTo.ONE);
    }

    Query query = new Query().setStale(Stale.FALSE);
    View view = client.getView(DESIGN_DOC_OBSERVE, VIEW_NAME_OBSERVE);
    HttpFuture<ViewResponse> future = client.asyncQuery(view, query);

    System.out.println(query.toString());
    System.out.println(view.getURI());

    ViewResponse response = future.get();
    Iterator<ViewRow> iterator = response.iterator();
    List<ViewRow> returnedRows = new ArrayList<ViewRow>();
    while (iterator.hasNext()) {
      System.out.println(((ViewRow)iterator.next()).getKey());
      returnedRows.add(iterator.next());
    }

    assertEquals(docAmount, returnedRows.size());
  }
