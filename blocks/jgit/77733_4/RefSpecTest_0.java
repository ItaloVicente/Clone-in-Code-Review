	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenSourceEndsWithSlash() {
		assertNotNull(new RefSpec("refs/heads/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenDestinationEndsWithSlash() {
		assertNotNull(new RefSpec("refs/heads/master:refs/heads/"));
	}

