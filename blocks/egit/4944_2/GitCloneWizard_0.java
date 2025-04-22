	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		try {
			if (nextPage == gerritConfiguration &&
					currentSearchResult.getGitRepositoryInfo().providesGerritConfiguration())
				return null;
		} catch (NoRepositoryInfoException e) {
			Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
		}
		return super.getNextPage(page);
	}

