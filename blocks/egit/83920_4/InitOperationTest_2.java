		assertPrefixEquals(FEATURE_PREFIX, FEATURE_KEY, repository);
		assertPrefixEquals(RELEASE_PREFIX, RELEASE_KEY, repository);
		assertPrefixEquals(HOTFIX_PREFIX, HOTFIX_KEY, repository);


		assertBranchEquals(DEVELOP, DEVELOP_KEY, repository);
		assertBranchEquals(MASTER, MASTER_KEY, repository);
	}

	private void assertPrefixEquals(String expected, String key,
			Repository repo) {
		assertEquals(
				"Unexpected value for key " + key + ", config: "
						+ repo.getConfig().toText(),
				expected, getPrefix(repo, key));
	}

	private void assertBranchEquals(String expected, String key,
			Repository repo) {
		assertEquals("Unexpected branch in: " + repo.getConfig().toText(),
				expected, getBranch(repo, key));
