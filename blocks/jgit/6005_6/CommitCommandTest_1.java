
	@Test
	public void shouldNotBePossibleToCreateEmptyCommitByDefault()
			throws Exception {
		boolean exceptionThrown = false;
		Git git = new Git(db);
		try {
		git.commit().setMessage("This is an empty commit!").call();
		} catch(JGitInternalException e) {
			assertEquals(JGitText.get().emptyCommit
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void shouldNotBePossibleToCreateEmptyCommitIfAllowEmptyIsSetToFalse()
			throws Exception {
		boolean exceptionThrown = false;
		Git git = new Git(db);
		try {
			git.commit().setMessage("This is an empty commit!")
				.setAllowEmpty(false).call();
		} catch(JGitInternalException e) {
			assertEquals(JGitText.get().emptyCommit
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
	}

	@Test
	public void shouldBePossibleToCreateEmptyCommitIfAllowEmptyIsSetToTrue()
			throws Exception {
		final String COMMIT_MESSAGE = "This is an empty commit!";
		Git git = new Git(db);
		assertTrue(git.status().call().isClean());
		RevCommit emptyCommit = git.commit().setMessage(COMMIT_MESSAGE)
				.setAllowEmpty(true).call();

		assertEquals(COMMIT_MESSAGE
	}
