  @Test(expected = InvalidViewException.class)
  public void testInvalidViewHandling() {
    String designDoc = "invalid_design";
    String viewName = "invalid_view";
    View view = client.getView(designDoc, viewName);
    assertNull(view);
  }

  @Test(expected = InvalidViewException.class)
  public void testInvalidDesignDocHandling() {
    String designDoc = "invalid_design";
    List<View> views = client.getViews(designDoc);
    assertNull(views);
  }

