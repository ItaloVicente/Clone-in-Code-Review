  @Override
  @Deprecated
  public HttpFuture<DesignDocument> asyncGetDesignDocument(
    final String designDocumentName) {
    return asyncGetDesignDoc(designDocumentName);
  }

  @Override
  @Deprecated
  public DesignDocument getDesignDocument(final String designDocumentName) {
    return getDesignDoc(designDocumentName);
  }

