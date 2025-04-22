		assertTrue(commitMessage.indexOf("Signed-off-by") > 0);
		commitDialogTester.commit();
	}

	@Test
	public void testAmend() throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		RevCommit oldHeadCommit = TestUtil.getHeadCommit(repository);
		commitOneFileChange("Yet another Change");
		RevCommit headCommit = TestUtil.getHeadCommit(repository);
		String changeId = CommitMessageUtil.extractChangeId(headCommit
				.getFullMessage());
		configureTestCommitterAsUser(repository);
		setTestFileContent("Changes over changes");
		CommitDialogTester commitDialogTester = CommitDialogTester
				.openCommitDialog(PROJ1);
		commitDialogTester.setAmend(true);
		assertTrue(commitDialogTester.getCommitMessage().indexOf("Change-Id") > 0);
		assertTrue(commitDialogTester.getCommitMessage().indexOf(
				"Signed-off-by") > 0);
		assertTrue(commitDialogTester.getSignedOff());
		assertTrue(commitDialogTester.getInsertChangeId());
		commitDialogTester.commit();
		headCommit = TestUtil.getHeadCommit(repository);
		assertEquals(oldHeadCommit, headCommit.getParent(0));
		assertTrue(headCommit.getFullMessage().indexOf(changeId) > 0);
