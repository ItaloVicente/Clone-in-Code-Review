	@Test
	public void testMergeWithChangeId() throws Exception {
		try (Git git = new Git(db)) {
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

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setInsertChangeId(true).call();

			assertNull(db.readMergeCommitMsg());

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			String commitMessage = newHead.getFullMessage();
			assertTrue(Pattern.compile("\nChange-Id: I[0-9a-fA-F]{40}\n")
					.matcher(commitMessage).find());
		}
	}

	@Test
	public void testMergeWithMessageAndChangeId() throws Exception {
		try (Git git = new Git(db)) {
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

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("user message").setInsertChangeId(true).call();

			assertNull(db.readMergeCommitMsg());

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			String commitMessage = newHead.getFullMessage();
			assertTrue(commitMessage.startsWith("user message\n\n"));
			assertTrue(Pattern.compile("\nChange-Id: I[0-9a-fA-F]{40}\n")
					.matcher(commitMessage).find());
		}
	}

