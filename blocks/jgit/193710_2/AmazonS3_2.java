	void authorize(final HttpURLConnection httpURLConnection
	    	final Map<String
			final byte[] data) throws IOException {
		if (awsApiSignatureVersion.equals("2")) {
			authorizeV2(httpURLConnection);
		}
		else if (awsApiSignatureVersion.equals("4")) {
			AwsRequestSignerV4.sign(httpURLConnection
		}
		else {
			throw new IllegalStateException(MessageFormat.format(
				JGitText.get().unexpectedAwsApiSignatureVersion
		}
	}

	void authorizeV2(HttpURLConnection c) throws IOException {
