  @Test
  public void testGetSpatialNoDocsView() {
    View view = client.getSpatialView(DESIGN_DOC_WO_REDUCE,
      VIEW_NAME_SPATIAL_ALL);
    Query query = new Query();
    query.setStale(Stale.FALSE);

    ViewResponse response = client.query(view, query);
    Iterator<ViewRow> iterator = response.iterator();
    assertEquals(ITEMS.size(), response.size());

    while(iterator.hasNext()) {
      ViewRowNoDocsSpatial row = (ViewRowNoDocsSpatial) iterator.next();
      assertNotNull(row.getBbox());
      assertNotNull(row.getGeometry());
      break;
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidGetSpatialNoDocsView() {
    View view = client.getSpatialView(DESIGN_DOC_WO_REDUCE,
      VIEW_NAME_SPATIAL_ALL);
    Query query = new Query();
    query.setStale(Stale.FALSE);

    ViewResponse response = client.query(view, query);
    Iterator<ViewRow> iterator = response.iterator();
    assertEquals(ITEMS.size(), response.size());

    while(iterator.hasNext()) {
      ViewRowNoDocsSpatial row = (ViewRowNoDocsSpatial) iterator.next();
      Object document = row.getDocument();
      break;
    }
  }

  @Test
  public void testGetSpatialDocsView() {
    View view = client.getSpatialView(DESIGN_DOC_WO_REDUCE,
      VIEW_NAME_SPATIAL_ALL);
    Query query = new Query();
    query.setStale(Stale.FALSE);
    query.setIncludeDocs(true);

    ViewResponse response = client.query(view, query);
    Iterator<ViewRow> iterator = response.iterator();
    assertEquals(ITEMS.size(), response.size());

    while(iterator.hasNext()) {
      ViewRowWithDocsSpatial row = (ViewRowWithDocsSpatial) iterator.next();
      assertNotNull(row.getBbox());
      assertNotNull(row.getGeometry());
      assertNotNull(row.getDocument());
      break;
    }
  }

