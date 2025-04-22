	@Test
	public void pushFinishInitiallyDisabledWhenNoBranch() throws Exception {
		checkoutNewLocalBranch("foo");

		PushBranchWizardTester wizard = PushBranchWizardTester
				.startWizard(selectProject(), "foo");
		wizard.selectRemote("fetch");
		wizard.enterBranchName("");
		assertFalse(wizard.canFinish());
		wizard.cancel();
	}

