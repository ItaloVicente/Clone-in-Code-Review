
	/**
	 * @param repos
	 *            available repositories
	 * @param suggestedRepo
	 *            relative path to git repository
	 * @return a repository mapping which corresponds to a suggested repository
	 *         location, <code>null</code> otherwise
	 */
	private RepositoryMapping findActualRepository(
			Collection<RepositoryMapping> repos, File suggestedRepo) {
		for (RepositoryMapping rm : repos) {
			if (Path.fromOSString(rm.getGitDir()).equals(Path.fromOSString(suggestedRepo.getPath())))
				return rm;
		}
		return null;
	}
