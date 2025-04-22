	@Test
	public void pushWithExistingUpstreamConfiguration() throws Exception {
		checkoutNewLocalBranch("foo");
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"foo", ConfigConstants.CONFIG_KEY_REMOTE, "fetch");
		repository.getConfig().setBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION, "foo",
				ConfigConstants.CONFIG_KEY_REBASE, true);
		repository.getConfig().setBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE, false);

		PushBranchWizardTester wizard = PushBranchWizardTester.startWizard(
				selectProject(), "foo");
		wizard.selectRemote("fetch");
		wizard.assertRebaseSelected();
		wizard.next();
		wizard.finish();

		assertBranchPushed("foo", remoteRepository);
		assertBranchConfig("foo", "fetch", "refs/heads/foo", "true");
	}

