	private void acceptError(org.eclipse.jgit.api.RebaseResult.Status status) {
		bot.button("Details >>").click();
		bot.list().select(NLS.bind(
				UIText.FeatureRebaseHandler_statusWas, status.toString()));
		bot.button("OK").click();
	}

	@Test
	public void testRebaseFailOnDirtyWorkingDirectory() throws Exception {
		ErrorDialog.AUTOMATED_MODE = false;

		Git git = Git.wrap(repository);

		init();
		setContentAddAndCommit("bar");

		createFeature(FEATURE_NAME);
		setContentAddAndCommit("foo");

		setTestFileContent("foobar");

		rebaseFeature();
		acceptError(RebaseResult.Status.UNCOMMITTED_CHANGES);

		Status status = git.status().call();
		Object[] uncommitted = status.getUncommittedChanges().toArray();
		assertEquals(1, uncommitted.length);
		assertEquals(FILE1_PATH, uncommitted[0]);
	}

