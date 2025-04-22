		page1 = new GitPreconfiguredSynchronizeWizardPage();
		page2 = new GitSynchronizeWizardPage();
		addPage(page1);
		addPage(page2);
	}

	@Override
	public boolean canFinish() {
		if (page1.requiresCustomeConfiguration())
			return page2.isPageComplete();

		return page1.isPageComplete();
