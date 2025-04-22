
	@Test(expected = JGitInternalException.class)
	public void testCheckoutOfConflictingFileShouldThrow()
			throws Exception {
		git.checkout().setCreateBranch(true).setName("conflict")
				.setStartPoint(initialCommit).call();
		writeTrashFile(FILE1
		RevCommit conflict = git.commit().setAll(true)
				.setMessage("Conflicting change").call();

		git.checkout().setName("master").call();

		git.merge().include(conflict).call();
		assertEquals(RepositoryState.MERGING

		git.checkout().addPath(FILE1).call();
	}
