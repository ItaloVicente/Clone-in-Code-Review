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
