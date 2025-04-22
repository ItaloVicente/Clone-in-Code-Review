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
    HttpFuture<Boolean> result;
    boolean success = true;
    try {
      result = client.asyncCreateDesignDoc(doc);
      assertTrue(result.get());
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

    Thread.sleep(2000);

    List<View> storedViews = client.getViews("mydesign");
    assertEquals(2, storedViews.size());
  }

  @Test
  public void testRawDesignDocumentCreation() throws InterruptedException {
    List<ViewDesign> views = new ArrayList<ViewDesign>();

    ViewDesign view = new ViewDesign(
      "viewname",
      "function(a, b) {}"
    );
    views.add(view);

    DesignDocument doc = new DesignDocument("rawdesign", views, null);
    HttpFuture<Boolean> result;
    boolean success = true;
    try {
      result = client.asyncCreateDesignDoc(doc.getName(), doc.toJson());
      assertTrue(result.get());
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

    Thread.sleep(2000);

    List<View> storedViews = client.getViews("rawdesign");
    assertEquals(1, storedViews.size());
  }

  @Test
  public void testInvalidDesignDocumentCreation() throws Exception {
    String content = "{certainly_not_a_view: true}";
    HttpFuture<Boolean> result = client.asyncCreateDesignDoc(
      "invalid_design", content);
    assertFalse(result.get());

    boolean success = false;
    try {
      client.getViews("invalid_design");
    } catch(InvalidViewException ex) {
      success = true;
    }
    assertTrue(success);
  }

  @Test
  public void testDesignDocumentDeletion() throws InterruptedException {
    List<View> storedViews = client.getViews("mydesign");
    assertEquals(2, storedViews.size());

    boolean success = true;

    try {
      HttpFuture<Boolean> result = client.asyncDeleteDesignDoc("mydesign");
      assertTrue(result.get());
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

    Thread.sleep(2000);

    success = false;
    try {
      storedViews = client.getViews("mydesign");
    } catch(InvalidViewException e) {
      success = true;
    }
    assertTrue(success);
  }

