
	@Test(expected = JGitInternalException.class)
	public void testCheckoutIndexOfConflictingFileShouldThrow()
			throws Exception {
		git.checkout().setCreateBranch(true).setName("conflict")
				.setStartPoint("master").call();
		writeTrashFile("Test.txt"
		RevCommit conflict = git.commit().setAll(true)
				.setMessage("Conflicting change").call();

		git.checkout().setName("test").call();

		git.merge().include(conflict).call();
		assertEquals(RepositoryState.MERGING

		git.checkout().addPath("Test.txt").call();
	}
