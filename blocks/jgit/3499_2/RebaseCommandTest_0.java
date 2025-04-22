
	@Test
	public void testRebaseShouldLeaveWorkspaceUntouchedWithUnstagedChangesConflict()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit initial = git.commit().setMessage("initial commit").call();
		createBranch(initial

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated FILE1 on master").call();

		checkoutBranch("refs/heads/side");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated FILE1 on side").call();

		File theFile = writeTrashFile(FILE1

		try {
			RebaseResult rebaseResult = git.rebase()
					.setUpstream("refs/heads/master").call();
			fail("Checkout with conflict should have occured
					+ rebaseResult.getStatus());
		} catch (JGitInternalException e) {
			checkFile(theFile
		}

		assertEquals(RepositoryState.SAFE
				.getRepositoryState());
	}
