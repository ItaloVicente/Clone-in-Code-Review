   * @param client the CouchbaseClient object to run the queries on.
   * @param view the instance of the View to run the Queries against.
   * @param query the instance of the Query object for the params.
   * @param numDocs the number of the documents to return per page (must be
   *    greater than zero).
   */
  public Paginator(CouchbaseClient client, View view, Query query,
      int numDocs) {
