  public HttpFuture<ViewResponse> asyncQuery(View view, Query query) {
    if (query.willReduce()) {
      return asyncQueryAndReduce(view, query);
    } else if (query.willIncludeDocs()) {
      return asyncQueryAndIncludeDocs(view, query);
    } else {
      return asyncQueryAndExcludeDocs(view, query);
    }
  }

