
  HttpFuture<Boolean> asyncCreateDesignDoc(final DesignDocument doc)
    throws UnsupportedEncodingException;
  HttpFuture<Boolean> asyncCreateDesignDoc(String name, String value)
    throws UnsupportedEncodingException;
  HttpFuture<Boolean> asyncDeleteDesignDoc(final String name)
    throws UnsupportedEncodingException;
  HttpFuture<DesignDocument> asyncGetDesignDocument(
    String designDocumentName);
  Boolean createDesignDoc(final DesignDocument doc);
  Boolean deleteDesignDoc(final String name);
  DesignDocument getDesignDocument(final String designDocumentName);

  Object getFromReplica(String key);
  <T> T getFromReplica(String key, Transcoder<T> tc);
  ReplicaGetFuture<Object> asyncGetFromReplica(final String key);
  <T> ReplicaGetFuture<T> asyncGetFromReplica(final String key,
    final Transcoder<T> tc);

  HttpFuture<View> asyncGetView(String designDocumentName,
      final String viewName);
  HttpFuture<SpatialView> asyncGetSpatialView(String designDocumentName,
      final String viewName);
  HttpFuture<ViewResponse> asyncQuery(AbstractView view, Query query);
  ViewResponse query(AbstractView view, Query query);
  Paginator paginatedQuery(View view, Query query, int docsPerPage);
  View getView(final String designDocumentName, final String viewName);
  SpatialView getSpatialView(final String designDocumentName,
    final String viewName);

  OperationFuture<Map<String, String>> getKeyStats(String key);

