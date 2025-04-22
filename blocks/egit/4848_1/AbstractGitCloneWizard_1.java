		try {
			GitRepositoryInfo info = new GitRepositoryInfo(uri.toString());
			info.setCredentials(credentials.getUser(), credentials.getPassword());
			return performClone(info);
		} catch (URISyntaxException e) {
			Activator.error(e.getMessage(), e);
			return false;
		}
	}

	protected boolean performClone(GitRepositoryInfo gitRepositoryInfo) throws URISyntaxException {
		URIish uri = new URIish(gitRepositoryInfo.getCloneUri());
		UserPasswordCredentials credentials = gitRepositoryInfo.getCredentials();
