
	private void selectRepositoryNode() throws Exception {
		SWTBotTreeItem repoNode = repoViewUtil.getRootItem(repoViewTree,
				repositoryFile);
		repoNode.select();
	}

	@Test
	public void testAmend() throws Exception {
		RevCommit oldHeadCommit = TestUtil.getHeadCommit(repository);
		commitOneFileChange("Yet another Change");
		RevCommit headCommit = TestUtil.getHeadCommit(repository);
		String changeId = CommitMessageUtil.extractChangeId(headCommit
				.getFullMessage());
		setTestFileContent("Changes over changes");
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAmend(true);
		assertTrue(stagingViewTester.getCommitMessage().indexOf("Change-Id") > 0);
		assertTrue(stagingViewTester.getCommitMessage().indexOf(
				"Signed-off-by") > 0);
		assertTrue(stagingViewTester.getSignedOff());
		assertTrue(stagingViewTester.getInsertChangeId());
		stagingViewTester.commit();
		headCommit = TestUtil.getHeadCommit(repository);
		assertEquals(oldHeadCommit, headCommit.getParent(0));
		assertTrue(headCommit.getFullMessage().indexOf(changeId) > 0);
	}

	private void commitOneFileChange(String fileContent) throws Exception {
		setTestFileContent(fileContent);
		new Git(repository).add().addFilepattern(".").call();
		selectRepositoryNode(); // update staging view after add
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTAUTHOR);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setInsertChangeId(true);
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue(commitMessage.indexOf("Change-Id") > 0);
		assertTrue(commitMessage.indexOf("Signed-off-by") > 0);
		stagingViewTester.commit();
	}
	
