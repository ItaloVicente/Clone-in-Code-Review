
  @Test
  public void testInvalidViewHandling() {
    String designDoc = "invalid_design";
    String viewName = "invalid_view";

    exception.expect(InvalidViewException.class);
    exception.expectMessage("Could not load view \""
                + viewName + "\" for design doc \"" + designDoc + "\"");
    View view = client.getView(designDoc, viewName);
    assertNull(view);
  }

  @Test
  public void testInvalidDesignDocHandling() {
    String designDoc = "invalid_design";

    exception.expect(InvalidViewException.class);
    exception.expectMessage("Could not load views for design doc \""
            + designDoc + "\"");
    List<View> views = client.getViews(designDoc);
    assertNull(views);
  }

