	@Override
	public String getWindowTitle() {
		final IWizardPage currentPage = getContainer().getCurrentPage();
		if (currentPage == repoPage || currentPage == null)
			return UIText.FetchWizard_windowTitleDefault;
		return NLS.bind(UIText.FetchWizard_windowTitleWithSource,
				getSourceString());
	}

