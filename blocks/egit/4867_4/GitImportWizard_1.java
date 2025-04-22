		try {
			return currentSearchResult.getGitRepositoryInfo().getCredentials();
		} catch (NoRepositoryInfoException e) {
			Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
			return null;
		} catch (Exception e) {
			Activator.error(e.getMessage(), e);
			return null;
		}
