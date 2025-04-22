  /**
   * Gets access to a spatial view contained in a design document from the
   * cluster.
   *
   * Spatial views enable you to return recorded geometry data in the bucket
   * and perform queries which return information based on whether the recorded
   * geometries existing within a given two-dimensional range such as a
   * bounding box.
   *
   * @param designDocumentName the name of the design document.
   * @param viewName the name of the view to get.
   * @return a SpatialView object from the cluster.
   * @throws InvalidViewException if no design document or view was found.
   * @throws CancellationException if operation was canceled.
   */
