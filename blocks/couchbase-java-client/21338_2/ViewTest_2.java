
  @Test
  public void testInvalidViewHandling() {
    String design_doc = "invalid_design";
    String view_name = "invalid_view";

    exception.expect(InvalidViewException.class);
    exception.expectMessage("Could not load view \"" +
                view_name + "\" for design doc \"" + design_doc + "\"");
    View view = client.getView(design_doc, view_name);
    assertNull(view);
  }

  @Test
  public void testInvalidDesignDocHandling() {
    String design_doc = "invalid_design";

    exception.expect(InvalidViewException.class);
    exception.expectMessage("Could not load views for design doc \""
            + design_doc + "\"");
    List<View> views = client.getViews(design_doc);
    assertNull(views);
  }

