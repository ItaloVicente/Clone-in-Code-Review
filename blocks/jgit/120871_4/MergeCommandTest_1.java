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
			Ref sideBranch = prepareSuccessfulMerge(git);
