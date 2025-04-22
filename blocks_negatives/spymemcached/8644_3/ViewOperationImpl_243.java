	public ViewOperationImpl(HttpRequest r, String bucketName,
			String designDocName, String viewName, ViewCallback viewCallback) {
		super(r, viewCallback);
		this.bucketName = bucketName;
		this.designDocName = designDocName;
		this.viewName = viewName;
	}
