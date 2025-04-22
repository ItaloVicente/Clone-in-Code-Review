				getCommit(localRightRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentCommits()
			throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD + "~1"));
