		CommitDialogTester commitDialogTester = CommitDialogTester
				.openCommitDialog(PROJ1);
		assertEquals("Wrong row count", 1, commitDialogTester.getRowCount());
		assertTrue("Wrong file",
				commitDialogTester.getEntryText(0).endsWith("test.txt"));
		commitDialogTester.setAuthor(TestUtil.TESTAUTHOR);
		commitDialogTester.setCommitter(TestUtil.TESTCOMMITTER);
		commitDialogTester.setCommitMessage("The new commit");
		commitDialogTester.commit();
		checkHeadCommit(TestUtil.TESTAUTHOR, TestUtil.TESTCOMMITTER, "The new commit");
		NoFilesToCommitPopup popup = CommitDialogTester
				.openCommitDialogExpectNoFilesToCommit(PROJ1);
		popup.cancelPopup();
	}

	private void checkHeadCommit(String author, String committer,
			String message) throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		CommitInfo commitInfo = CommitHelper.getHeadCommitInfo(repository);
		assertEquals(author, commitInfo.getAuthor());
		assertEquals(committer, commitInfo.getCommitter());
		assertEquals(message, commitInfo.getCommitMessage());
