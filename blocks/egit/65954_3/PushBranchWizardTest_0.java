	@Test
	public void pushHeadToExistingRemote() throws Exception {
		try (Git git = new Git(repository)) {
			AnyObjectId head = repository.resolve(Constants.HEAD);
			git.checkout().setName(head.name()).call();
		}

		PushBranchWizardTester wizard = PushBranchWizardTester
				.startWizard(selectProject(), Constants.HEAD);
		wizard.selectRemote("fetch");
		wizard.enterBranchName("foo");
		wizard.next();
		wizard.finish();

		assertBranchPushed("foo", remoteRepository);
	}

