	@TestAllImplementations
	void testCloneWithDepth(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		remoteRepository.getRepository().getConfig().setInt("protocol"
				"version"
