	@TestAllImplementations
	void testCloneWithDeepenNot(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		remoteRepository.getRepository().getConfig().setInt("protocol"
				"version"
		RevCommit commit = remoteRepository.git().log().call().iterator()
				.next();
		remoteRepository.update(master
				.message("Test").add("test.txt"
