  /**
   * Gets access to a spatial view contained in a design document from the
   * cluster.
   *
   *
   * Note that since an HttpFuture is returned, the caller must also check to
   * see if the View is null. The HttpFuture does provide a getStatus() method
   * which can be used to check whether or not the view request has been
   * successful.
   *
   * @param designDocumentName the name of the design document.
   * @param viewName the name of the spatial view to get.
   * @return a HttpFuture<SpatialView> object from the cluster.
   * @throws InterruptedException if the operation is interrupted while in
   *           flight
   * @throws ExecutionException if an error occurs during execution
   */
