	@TestAllImplementations
	void testHttpClientWantsV2AndServerNotConfigured(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		String url = smartAuthNoneURI.toString()
				+ "/info/refs?service=git-upload-pack";
