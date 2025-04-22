	@Test
	void commitAmendOnInitialShouldFail() throws Exception {
		assertThrows(WrongRepositoryStateException.class
			try (Git git = new Git(db)) {
				git.commit().setAmend(true).setMessage("initial commit").call();
			}
		});
