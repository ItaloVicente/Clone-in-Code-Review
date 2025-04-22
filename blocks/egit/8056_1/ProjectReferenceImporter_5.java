	private static File findConfiguredRepository(URIish gitUrl) {
		for (String repoDir : getRepositoryUtil().getConfiguredRepositories()) {
			File repoDirFile = new File(repoDir);
			if (repositoryAlreadyExistsForUrl(repoDirFile, gitUrl))
				return repoDirFile;
		}
		return null;
	}

