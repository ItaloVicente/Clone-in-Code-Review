		try {
			if (currentSearchResult.getGitRepositoryInfo().shouldSaveCredentialsInSecureStore()) {
				SecureStoreUtils.storeCredentials(currentSearchResult.getGitRepositoryInfo().getCredentials(),
						new URIish(currentSearchResult.getGitRepositoryInfo().getCloneUri()));
			}
		} catch (Exception e) {
			Activator.error(e.getMessage(), e);
		}
