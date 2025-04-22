  public HttpFuture<ViewResponse> asyncQuery(View view, Query query) {
    String queryString = query.toString();
    String params = (queryString.length() > 0) ? "&reduce=false"
        : "?reduce=false";

    String uri = view.getURI() + queryString + params;
