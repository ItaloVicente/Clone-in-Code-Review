	@Override
	public void handleResponse(HttpResponse response) {
		String json = getEntityString(response);
		try {
			View view = parseDesignDocumentForView(bucketName, designDocName,
					viewName, json);
