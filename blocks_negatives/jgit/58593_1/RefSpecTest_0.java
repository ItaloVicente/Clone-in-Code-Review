	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenWildcardAfterText() {
		assertNotNull(new RefSpec("refs/heads/wrong*:refs/heads/right/*"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenWildcardBeforeText() {
		assertNotNull(new RefSpec("*wrong:right/*"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenWildcardBeforeTextAtEnd() {
		assertNotNull(new RefSpec("refs/heads/*wrong:right/*"));
	}

