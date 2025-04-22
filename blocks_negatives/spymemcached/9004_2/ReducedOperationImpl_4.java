	public void handleResponse(HttpResponse response) {
		String json = getEntityString(response);
		int errorcode = response.getStatusLine().getStatusCode();
		try {
			OperationStatus status = parseViewForStatus(json, errorcode);
			ViewResponseReduced vr = null;
			if (status.isSuccess()) {
				vr = parseReducedViewResult(json);
			}

			((ReducedCallback) callback).gotData(vr);
			callback.receivedStatus(status);
		} catch (ParseException e) {
			exception = new OperationException(OperationErrorType.GENERAL,
					"Error parsing JSON");
		}
		callback.complete();
	}

	private ViewResponseReduced parseReducedViewResult(String json)
