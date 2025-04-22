	protected boolean hasSearchResult() {
		return currentSearchResult != null;
	}

	private void addRepositorySearchPage() {
		if (currentSearchResult instanceof WizardPage) {
			addPage((WizardPage)currentSearchResult);
		}
	}

	private void addRepositoryLocationPage() {
		List<CloneSourceProvider> cloneSourceProviders = getCloneSourceProviders();
		if (hasSingleCloneSourceProviderWithFixedLocation(cloneSourceProviders))
			try {
				addPage(cloneSourceProviders.get(0).getRepositorySearchPage());
			} catch (CoreException e) {
				Activator.logError(e.getLocalizedMessage(), e);
			}
		else
			addPage(new RepositoryLocationPage(cloneSourceProviders));
	}

	private boolean hasSingleCloneSourceProviderWithFixedLocation(
			List<CloneSourceProvider> cloneSourceProviders) {
		return cloneSourceProviders.size() == 1 && cloneSourceProviders.get(0).hasFixLocation();
	}

