  public SpatialView getSpatialView(final String designDocumentName,
    final String viewName) {
    try {
      SpatialView view = asyncGetSpatialView(designDocumentName, viewName)
        .get();
      if(view == null) {
        throw new InvalidViewException("Could not load spatial view \""
          + viewName + "\" for design doc \"" + designDocumentName + "\"");
      }
      return view;
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted getting spatial view", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed getting view", e);
    }
  }

