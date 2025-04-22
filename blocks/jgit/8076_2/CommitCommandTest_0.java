
	@Test(expected = WrongRepositoryStateException.class)
	public void commitAmendOnInitialShouldFail() throws Exception {
		Git git = new Git(db);
		git.commit().setAmend(true).setMessage("initial commit").call();
	}
