	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page instanceof IRepositorySearchResult) {
			currentSearchResult = (IRepositorySearchResult)page;
			return validSource;
		}
		return super.getNextPage(page);
	}

	protected RepositorySelection getRepositorySelection() {
		try {
			return (new RepositorySelection(new URIish(currentSearchResult.getGitRepositoryInfo().getCloneUri()), null));
		} catch (URISyntaxException e) {
			Activator.error(UIText.GitImportWizard_errorParsingURI, e);
			return null;
		} catch (NoRepositoryInfoException e) {
			Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
			return null;
		} catch (Exception e) {
			Activator.error(e.getMessage(), e);
			return null;
		}
	}

	protected UserPasswordCredentials getCredentials() {
		try {
			return currentSearchResult.getGitRepositoryInfo().getCredentials();
		} catch (NoRepositoryInfoException e) {
			Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
			return null;
		} catch (Exception e) {
			Activator.error(e.getMessage(), e);
			return null;
		}
	}

