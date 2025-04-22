  public ViewFuture asyncQuery(View view, Query query) {
    return asyncQuery(view, query, false);
  }

  public ViewFuture asyncQuery(View view, Query query,
      boolean exceptionOnError) {
