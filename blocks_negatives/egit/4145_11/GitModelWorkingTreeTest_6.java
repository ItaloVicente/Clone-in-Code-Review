		assertFalse(!actual);
	}

	@Test public void shouldReturnNotEqualsForTheDifferentParents()
			throws Exception {
		File localRightRepoFile = createProjectAndCommitToRepository(REPO2);
		GitModelRepository rightGsd = new GitModelRepository(
				getGSD(lookupRepository(localRightRepoFile)));
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit());
		GitModelWorkingTree right = new GitModelWorkingTree(rightGsd);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqualsForTheSameCommits()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit());
		GitModelWorkingTree right = new GitModelWorkingTree(
				createModelCommit());

		boolean actual = left.equals(right);

		assertFalse(!actual);
