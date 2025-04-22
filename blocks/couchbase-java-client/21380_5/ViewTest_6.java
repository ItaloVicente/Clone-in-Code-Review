
  @Test
  public void testDesignDocumentCreation() throws InterruptedException {
    List<ViewDesign> views = new ArrayList<ViewDesign>();
    List<SpatialViewDesign> spviews = new ArrayList<SpatialViewDesign>();

    ViewDesign view1 = new ViewDesign(
      "view1",
      "function(a, b) {}"
    );
    views.add(view1);

    ViewDesign view2 = new ViewDesign(
      "view2",
      "function(b, c) {}",
      "function(red) {}"
    );
    views.add(view2);

    SpatialViewDesign spview = new SpatialViewDesign(
      "spatialfoo",
      "function(map) {}"
    );
    spviews.add(spview);

    DesignDocument doc = new DesignDocument("mydesign", views, spviews);
    HttpFuture<String> result;
    boolean success = true;
    try {
      result = client.asyncCreateDesignDoc(doc);
      result.get();
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

    Thread.sleep(2000);

    List<View> storedViews = client.getViews("mydesign");
    assertEquals(2, storedViews.size());
  }

  @Test
  public void testDesignDocumentDeletion() throws InterruptedException {
    List<View> storedViews = client.getViews("mydesign");
    assertEquals(2, storedViews.size());

    boolean success = true;
    try {
      HttpFuture<String> result = client.asyncDeleteDesignDoc("mydesign");
      result.get();
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

    Thread.sleep(2000);

    storedViews = client.getViews("mydesign");
    assertEquals(null, storedViews);
  }

