	@TestAllImplementations
	void testV2CloneWithDeepenNot(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		RevCommit commit = remoteRepository.git().log().call().iterator()
				.next();
		remoteRepository.update(master
				.message("Test").add("test.txt"

		File directory = createTempDirectory("testV2CloneWithDeepenNot");
		Git git = Git.cloneRepository().setDirectory(directory)
				.addShallowExclude(commit.getId())
				.setURI(smartAuthNoneURI.toString()).call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
				git.getRepository().getObjectDatabase().getShallowCommits());
	}
