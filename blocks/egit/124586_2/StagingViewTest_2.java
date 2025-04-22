		return stagingViewTester;
	}

	@Test
	public void testCommitMessageCommitterChangeSignOff() throws Exception {
		setContent("something");

		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTAUTHOR);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") > 0);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER.substring(0,
				TestUtil.TESTCOMMITTER.length() - 1));
		assertTrue("Sign off should still be enabled",
				stagingViewTester.getSignedOff());
		assertEquals("Commit message should be unchanged", commitMessage,
				stagingViewTester.getCommitMessage());
		stagingViewTester.setCommitter("Somebody <some.body@some.where.org>");
		assertEquals("Commit message should be updated",
				commitMessage.replace(TestUtil.TESTCOMMITTER,
						"Somebody <some.body@some.where.org>"),
				stagingViewTester.getCommitMessage());
		assertTrue("Sign off should still be enabled",
				stagingViewTester.getSignedOff());
	}

	@Test
	public void testCommitMessageConfigChange() throws Exception {
		setContent("something");

		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTAUTHOR);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") > 0);
		StoredConfig cfg = repository.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, "Some One");
		cfg.save();
		TestUtil.processUIEvents();
		String expectedCommitter = "Some One <" + TestUtil.TESTCOMMITTER_EMAIL
				+ '>';
		assertEquals("Author should be unchanged", TestUtil.TESTAUTHOR,
				stagingViewTester.getAuthor());
		assertEquals("Committer should be changed", expectedCommitter,
				stagingViewTester.getCommitter());
		assertEquals("Commit message should be updated",
				commitMessage.replace(TestUtil.TESTCOMMITTER,
						expectedCommitter),
				stagingViewTester.getCommitMessage());
		assertTrue("Sign-off should be enabled",
				stagingViewTester.getSignedOff());
	}

	@Test
	public void testCommitMessageConfigChangeNoSignOff() throws Exception {
		setContent("something");

		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTAUTHOR);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") > 0);
		stagingViewTester.setSignedOff(false);
		commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should not have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") < 0);
		StoredConfig cfg = repository.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, "Some One");
		cfg.save();
		TestUtil.processUIEvents();
		String expectedCommitter = "Some One <" + TestUtil.TESTCOMMITTER_EMAIL
				+ '>';
		assertEquals("Author should be unchanged", TestUtil.TESTAUTHOR,
				stagingViewTester.getAuthor());
		assertEquals("Committer should be changed", expectedCommitter,
				stagingViewTester.getCommitter());
		assertEquals("Commit message should be unchanged", commitMessage,
				stagingViewTester.getCommitMessage());
		assertFalse("Sign-off should be disabled",
				stagingViewTester.getSignedOff());
	}

	@Test
	public void testCommitMessageConfigChangeWithAuthor() throws Exception {
		setContent("something");

		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") > 0);
		StoredConfig cfg = repository.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, "Some One");
		cfg.save();
		TestUtil.processUIEvents();
		String expectedCommitter = "Some One <" + TestUtil.TESTCOMMITTER_EMAIL
				+ '>';
		assertEquals("Author should be changed", expectedCommitter,
				stagingViewTester.getAuthor());
		assertEquals("Committer should be changed", expectedCommitter,
				stagingViewTester.getCommitter());
		assertEquals("Commit message should be updated",
				commitMessage.replace(TestUtil.TESTCOMMITTER,
						expectedCommitter),
				stagingViewTester.getCommitMessage());
		assertTrue("Sign-off should be enabled",
				stagingViewTester.getSignedOff());
	}

	@Test
	public void testCommitMessageConfigChangeBranchSwitchToNew()
			throws Exception {
		setContent("something");
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAuthor(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitter(TestUtil.TESTCOMMITTER);
		stagingViewTester.setCommitMessage("Commit message");
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Should have a signed-off footer",
				commitMessage.indexOf("Signed-off-by") > 0);
		try (Git git = new Git(repository)) {
			git.checkout().setAllPaths(true).setCreateBranch(true)
					.setName("refs/heads/myBranch").call();
		}
		TestUtil.joinJobs(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		TestUtil.processUIEvents();
		assertEquals("Author should be unchanged", TestUtil.TESTCOMMITTER,
				stagingViewTester.getAuthor());
		assertEquals("Committer should be unchanged", TestUtil.TESTCOMMITTER,
				stagingViewTester.getCommitter());
		assertEquals("Commit message should be unchanged", commitMessage,
				stagingViewTester.getCommitMessage());
		assertTrue("Sign-off should be enabled",
				stagingViewTester.getSignedOff());

	}

	@Test
	public void testCommitMessageConfigChangeAmending() throws Exception {
		setContent("something");
		try (Git git = new Git(repository)) {
			git.add().addFilepattern(FILE1_PATH).call();
			PersonIdent author = RawParseUtils
					.parsePersonIdent(TestUtil.TESTAUTHOR);
			git.commit().setAuthor(author).setCommitter(author)
					.setMessage("Author's commit\n\nSigned-off-by: "
							+ TestUtil.TESTAUTHOR)
					.call();
		}
		StagingViewTester stagingViewTester = StagingViewTester
				.openStagingView();
		stagingViewTester.setAmend(true);
		stagingViewTester.setSignedOff(true);
		String commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Commit message should have two signed-off lines",
				commitMessage.contains(TestUtil.TESTAUTHOR)
						&& commitMessage.contains(TestUtil.TESTCOMMITTER));
		StoredConfig cfg = repository.getConfig();
		cfg.load();
		cfg.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, "Some One");
		cfg.save();
		TestUtil.processUIEvents();
		assertEquals("Author should be unchanged", TestUtil.TESTAUTHOR,
				stagingViewTester.getAuthor());
		String expectedCommitter = "Some One <" + TestUtil.TESTCOMMITTER_EMAIL
				+ '>';
		assertEquals("Committer should be changed", expectedCommitter,
				stagingViewTester.getCommitter());
		assertTrue("Sign-off should be enabled",
				stagingViewTester.getSignedOff());
		assertTrue("Amend should be enabled", stagingViewTester.getAmend());
		commitMessage = stagingViewTester.getCommitMessage();
		assertTrue("Commit message should have two signed-off lines",
				commitMessage.contains(TestUtil.TESTAUTHOR)
						&& commitMessage.contains(expectedCommitter)
						&& !commitMessage.contains(TestUtil.TESTCOMMITTER));
