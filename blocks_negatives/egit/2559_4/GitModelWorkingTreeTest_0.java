	@Test public void shouldReturnNotEqualsForTheDifferentCommits()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));
		GitModelWorkingTree right = new GitModelWorkingTree(
				createModelCommit(),
				getCommit(leftRepoFile, HEAD + "~1"));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

