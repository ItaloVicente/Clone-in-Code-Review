	@TestAllImplementations
	void testHttpServerConfiguredToV0(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		remoteRepository.getRepository().getConfig().setInt("protocol"
				"version"
		String url = smartAuthNoneURI.toString()
				+ "/info/refs?service=git-upload-pack";
