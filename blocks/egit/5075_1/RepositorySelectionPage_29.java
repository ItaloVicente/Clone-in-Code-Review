	public GitRepositoryInfo getGitRepositoryInfo() {
		GitRepositoryInfo info = new GitRepositoryInfo(uri.toString());
		info.setCredentials(user, password);
		info.setShouldSaveCredentialsInSecureStore(true);
		uriProposalHandler.updateProposals();
		return info;
	}

