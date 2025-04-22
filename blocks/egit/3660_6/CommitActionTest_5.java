		commitDialogTester = noFilesToCommitPopup.confirmPopup();
		assertTrue(commitDialogTester.getCommitMessage().indexOf("Change-Id") > 0);
	}

	@Test
	public void testLaunchedWithAmend() throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RevCommit oldHeadCommit = TestUtil.getHeadCommit(repository);
		commitOneFileChange("Again another Change");
		configureTestCommitterAsUser(repository);
		NoFilesToCommitPopup noFilesToCommitPopup = CommitDialogTester
				.openCommitDialogExpectNoFilesToCommit(PROJ1);
		CommitDialogTester commitDialogTester = noFilesToCommitPopup.confirmPopup();
		assertTrue(commitDialogTester.getCommitMessage().indexOf("Change-Id") > 0);
		assertTrue(commitDialogTester.getCommitMessage().indexOf("Signed-off-by") > 0);
		assertTrue(commitDialogTester.getAmend());
		assertTrue(commitDialogTester.getSignedOff());
		assertTrue(commitDialogTester.getInsertChangeId());
		commitDialogTester.commit();
		RevCommit headCommit = TestUtil.getHeadCommit(repository);
		assertEquals(oldHeadCommit, headCommit.getParent(0));
	}

	private void commitOneFileChange(String fileContent) throws Exception {
		setTestFileContent(fileContent);
		CommitDialogTester commitDialogTester = CommitDialogTester
				.openCommitDialog(PROJ1);
		assertEquals("Wrong row count", 1, commitDialogTester.getRowCount());
		assertTrue("Wrong file",
				commitDialogTester.getEntryText(0).endsWith("test.txt"));
		commitDialogTester.setAuthor(TestUtil.TESTAUTHOR);
		commitDialogTester.setCommitter(TestUtil.TESTCOMMITTER);
		commitDialogTester.setCommitMessage("Commit message");
		commitDialogTester.setInsertChangeId(true);
		commitDialogTester.setSignedOff(true);

		String commitMessage = commitDialogTester.getCommitMessage();
