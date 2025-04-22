  /**
   * Queries a Couchbase view and returns the result.
   * The result can be accessed row-wise via an iterator.
   * This type of query will return the view result along
   * with all of the documents for each row in
   * the query.
   *
   * @param view the view to run the query against.
   * @param query the type of query to run against the view.
   * @return a ViewResponseWithDocs containing the results of the query.
   * @throws CancellationException if operation was canceled.
   */
