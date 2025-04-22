	@Test
	public void testCloneWithDeepenNot() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol", null, "version", 0);
		RevCommit commit = remoteRepository.git().log().call().iterator().next();
		remoteRepository.update(master, remoteRepository.commit()
														.parent(commit)
														.message("Test")
														.add("test.txt", "Hello world")
														.create());
