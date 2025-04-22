		AuthHeadersResponse response = null;
		try {
			response = new AuthHeadersResponse(headers);
		} catch (IOException e) {
			fail("Couldn't instantiate AuthHeadersResponse: " + e.toString());
		}
		HttpAuthMethod authMethod = HttpAuthMethod.scanResponse(response);
