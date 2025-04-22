	void authorize(HttpURLConnection httpURLConnection
			Map<String
			final String bodyHash) throws IOException {
		if (awsApiSignatureVersion.equals(AWS_API_V2)) {
			authorizeV2(httpURLConnection);
		} else if (awsApiSignatureVersion.equals(AWS_API_V4)) {
			AwsRequestSignerV4.sign(httpURLConnection
					region
		}
	}

	void authorizeV2(HttpURLConnection c) throws IOException {
