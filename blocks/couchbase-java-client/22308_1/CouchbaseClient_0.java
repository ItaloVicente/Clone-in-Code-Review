  public View getSpatialView(final String designDocumentName,
    final String viewName) {
    try {
      View view = asyncGetView(designDocumentName, viewName,
        ViewType.SPATIAL).get();
      if(view == null) {
        throw new InvalidViewException("Could not load spatial view \""
          + viewName + "\" for design doc \"" + designDocumentName + "\"");
      }
      return view;
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted getting views", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed getting views", e);
    }
  }

