	@Test
	public void pushWithLocalUpstreamConfiguration() throws Exception {
		checkoutNewLocalBranch("foo");
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"foo", ConfigConstants.CONFIG_KEY_REMOTE, ".");
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"foo", ConfigConstants.CONFIG_KEY_MERGE, "refs/heads/master");

		PushBranchWizardTester wizard = PushBranchWizardTester.startWizard(
				selectProject(), "foo");
		wizard.selectRemote("fetch");
		wizard.assertBranchName("foo");
		wizard.assertMergeSelected();
		assertTrue(wizard.isUpstreamConfigOverwriteWarningShown());
		wizard.deselectConfigureUpstream();
		assertFalse(wizard.isUpstreamConfigOverwriteWarningShown());
		wizard.selectMerge();
		wizard.next();
		wizard.finish();

		ObjectId remoteId = remoteRepository.resolve("foo");
		ObjectId localId = repository.resolve("foo");
		assertEquals(localId, remoteId);

		assertBranchConfig("foo", "fetch", "refs/heads/foo", null);
	}

