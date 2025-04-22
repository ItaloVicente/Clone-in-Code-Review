	@Test
	public void testNotToAddFilemode0() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("b"
		new File(git.getRepository().getWorkTree()
		RevCommit sideCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side2");
		writeTrashFile("b"
		new File(git.getRepository().getWorkTree()
		addAllAndCommit(git);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING
	}

