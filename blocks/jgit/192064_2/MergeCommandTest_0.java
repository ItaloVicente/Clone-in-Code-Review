	@Test
	public void testMergeConflictWithMessageAndCommentChar() throws Exception {
		try (Git git = new Git(db)) {
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

			StoredConfig config = db.getConfig();
			config.setString("core"

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("user message").call();

			assertEquals("user message\n\n^ Conflicts:\n^\ta\n"
					db.readMergeCommitMsg());
		}
	}

	@Test
	public void testMergeConflictWithMessageAndCommentCharAuto()
			throws Exception {
		try (Git git = new Git(db)) {
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

			StoredConfig config = db.getConfig();
			config.setString("core"

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("#user message").call();

			assertEquals("#user message\n\n; Conflicts:\n;\ta\n"
					db.readMergeCommitMsg());
		}
	}

