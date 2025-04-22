	@Override
	public void handleResponse(HttpResponse response) {
		String json = getEntityString(response);
		int errorcode = response.getStatusLine().getStatusCode();
		try {
			OperationStatus status = parseViewForStatus(json, errorcode);
			ViewResponseNoDocs vr = null;
			if (status.isSuccess()) {
				vr = parseNoDocsViewResult(json);
			}
