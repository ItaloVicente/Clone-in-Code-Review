	public Response.Action getDownloadAction(AnyLongObjectId oid) {
		URL endpointUrl = getObjectUrl(region

		Map<String
		queryParams.put("X-Amz-Expires"

		Map<String

		String authorizationQueryParameters = computeSignature(endpointUrl
				"GET"
				region
