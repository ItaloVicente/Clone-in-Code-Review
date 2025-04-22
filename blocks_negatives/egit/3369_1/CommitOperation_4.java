	/**
	 *
	 * @param repos
	 */
	public void setRepos(Repository[] repos) {
		if (repos != null) {
			this.repos = new Repository[repos.length];
			System.arraycopy(repos, 0, this.repos, 0, repos.length);
		} else
			this.repos = null;
	}

