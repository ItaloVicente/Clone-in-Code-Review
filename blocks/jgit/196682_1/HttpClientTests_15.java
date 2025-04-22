		Git git = Git.cloneRepository().setDirectory(directory)
				.addShallowExclude(commit.getId())
				.setURI(smartAuthNoneURI.toString()).call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
				git.getRepository().getObjectDatabase().getShallowCommits());
	}

	@TestAllImplementations
	void testV2CloneWithDepth(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		File directory = createTempDirectory("testV2CloneWithDepth");
		Git git = Git.cloneRepository().setDirectory(directory).setDepth(1)
				.setURI(smartAuthNoneURI.toString()).call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
				git.getRepository().getObjectDatabase().getShallowCommits());
	}

	@TestAllImplementations
	void testV2CloneWithDeepenSince(
			@SuppressWarnings("unused") HttpConnectionFactory cf)
			throws Exception {
		RevCommit commit = remoteRepository.commit()
				.parent(remoteRepository.git().log().call().iterator().next())
				.message("Test").add("test.txt"
		remoteRepository.update(master

		File directory = createTempDirectory("testV2CloneWithDeepenSince");
		Git git = Git.cloneRepository().setDirectory(directory)
				.setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()))
				.setURI(smartAuthNoneURI.toString()).call();
