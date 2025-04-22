	@Test
	public void testMergeWithMessageOption() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("main").call();

		Ref sideBranch = db.getRef("side");

		git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
				.setMessage("user message").call();

		assertNull(db.readMergeCommitMsg());

		Iterator<RevCommit> it = git.log().call().iterator();
		RevCommit newHead = it.next();
		assertEquals("user message"
	}

	@Test
	public void testMergeConflictWithMessageOption() throws Exception {
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

		git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
				.setMessage("user message").call();

		assertEquals("user message\n\nConflicts:\n\ta\n"
				db.readMergeCommitMsg());
	}

