	public GitRepositoryInfo getGitRepositoryInfo() {
		GitRepositoryInfo info = new GitRepositoryInfo(uri.toString());
		info.setCredentials(user, password);
		return info;
	}

