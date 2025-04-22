		CommitDialogTester commitDialogTester = CommitDialogTester
				.openCommitDialog(PROJ1);
		assertEquals("Wrong row count", 1, commitDialogTester.getRowCount());
		assertTrue("Wrong file",
				commitDialogTester.getEntryText(0).endsWith("test.txt"));
		commitDialogTester.setAuthor(TestUtil.TESTAUTHOR);
		commitDialogTester.setCommitter(TestUtil.TESTCOMMITTER);
		String commitMessage = commitDialogTester.getCommitMessage();
