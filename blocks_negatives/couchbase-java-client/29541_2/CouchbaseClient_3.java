  /**
   * Gets access to a view contained in a design document from the cluster.
   *
   * The purpose of a view is take the structured data stored within the
   * Couchbase Server database as JSON documents, extract the fields and
   * information, and to produce an index of the selected information.
   *
   * The result is a view on the stored data. The view that is created
   * during this process allows you to iterate, select and query the
   * information in your database from the raw data objects that have
   * been stored.
   *
   * @param designDocumentName the name of the design document.
   * @param viewName the name of the view to get.
   * @return a View object from the cluster.
   * @throws InvalidViewException if no design document or view was found.
   * @throws CancellationException if operation was canceled.
   */
