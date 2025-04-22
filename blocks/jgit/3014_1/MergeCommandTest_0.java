	@Test
	public void testMergeMessage() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("main").call();

		Ref sideBranch = db.getRef("side");

		git.merge().include(sideBranch)
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals("Merge branch 'side'\n\nConflicts:\n\ta\n"
				db.readMergeCommitMsg());

	}

