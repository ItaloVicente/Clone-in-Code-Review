  public ViewsOperationImpl(HttpRequest r, String bucketName,
      String designDocName, ViewsCallback viewsCallback) {
    super(r, viewsCallback);
    this.bucketName = bucketName;
    this.designDocName = designDocName;
  }
