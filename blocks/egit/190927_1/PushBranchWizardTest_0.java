	@Test
	public void pushFinishInitiallyPossible() throws Exception {
		checkoutNewLocalBranch("foo");

		PushBranchWizardTester wizard = PushBranchWizardTester
				.startWizard(selectProject(), "foo");
		wizard.selectRemote("fetch");
		wizard.selectMerge();
		wizard.finish();

		assertBranchPushed("foo", remoteRepository);
		assertBranchConfig("foo", "fetch", "refs/heads/foo", "false");
	}

	@Test
	public void pushFinishWithConfirmation() throws Exception {
		checkoutNewLocalBranch("foo");

		PushBranchWizardTester wizard = PushBranchWizardTester
				.startWizard(selectProject(), "foo");
		wizard.selectRemote("fetch");
		wizard.selectMerge();
		assertTrue(wizard.canFinish());
		wizard.next();
		wizard.prev();
		assertFalse(wizard.canFinish());
		wizard.next();
		wizard.finish();

		assertBranchPushed("foo", remoteRepository);
		assertBranchConfig("foo", "fetch", "refs/heads/foo", "false");
	}

