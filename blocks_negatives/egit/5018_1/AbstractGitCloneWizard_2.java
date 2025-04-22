	protected boolean performClone(URIish uri,
			UserPasswordCredentials credentials) {
		try {
			GitRepositoryInfo info = new GitRepositoryInfo(uri.toString());
			if (credentials != null)
				info.setCredentials(credentials.getUser(), credentials.getPassword());
			return performClone(info);
		} catch (URISyntaxException e) {
			Activator.error(e.getMessage(), e);
			return false;
		}
