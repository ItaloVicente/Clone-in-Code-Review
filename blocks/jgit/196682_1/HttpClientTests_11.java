	@TestAllImplementations
	void testV2HttpFirstResponse(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		String url = smartAuthNoneURI.toString()
				+ "/info/refs?service=git-upload-pack";
