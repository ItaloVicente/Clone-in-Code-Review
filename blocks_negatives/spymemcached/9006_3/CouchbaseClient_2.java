  /**
   * Queries a Couchbase view by calling its map function. This type of query
   * will return the view result but will not get the documents associated with
   * each row of the query.
   *
   * @param view the view to run the query against.
   * @param query the type of query to run against the view.
   * @return a ViewResponseNoDocs containing the results of the query.
   */
  public ViewResponse queryAndExcludeDocs(View view, Query query) {
    try {
      return asyncQueryAndExcludeDocs(view, query).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted while accessing the view", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed to access the view", e);
    }
  }

  /**
   * Queries a Couchbase view by calling its map function and then the views
   * reduce function.
   *
   * @param view the view to run the query against.
   * @param query the type of query to run against the view.
   * @return a Future containing the results of the query.
   */
  public ViewResponse queryAndReduce(View view, Query query) {
    try {
      return asyncQueryAndReduce(view, query).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted while accessing the view", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed to access the view", e);
    }
  }

