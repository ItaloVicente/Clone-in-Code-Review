	void authorizeV4(final HttpURLConnection httpURLConnection
    		final Map<String
		final Map<String
		httpURLConnection.getRequestProperties().forEach((headerName
			headers.put(headerName
		final String authorization = signer.computeSignature(headers
		httpURLConnection.setRequestProperty("Authorization"
	}

