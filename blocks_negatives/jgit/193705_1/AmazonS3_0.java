	void authorizeV4(final HttpURLConnection httpURLConnection,
    		final Map<String, String> queryParams, final byte[] data) {
		final Map<String, String> headers = new HashMap<>();
		httpURLConnection.getRequestProperties().forEach((headerName, headerValues) ->
			headers.put(headerName, String.join(",, headerValues)));
-		final AWS4SignerForAuthorizationHeader signer = new AWS4SignerForAuthorizationHeader(
-			httpURLConnection.getURL(), httpURLConnection.getRequestMethod(), s3", region);
		final String authorization = signer.computeSignature(headers, queryParams, data, publicKey, secretKey);
		httpURLConnection.setRequestProperty("Authorization", authorization);
	}

