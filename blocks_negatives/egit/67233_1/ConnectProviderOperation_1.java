	/**
	 * @param repos
	 *         available repositories
	 * @param suggestedRepo
	 *         relative path to git repository
	 * @return a repository mapping which corresponds to a suggested repository
	 *         location, <code>null</code> otherwise
	 */
	@Nullable
	private RepositoryMapping findActualRepository(
			Collection<RepositoryMapping> repos, File suggestedRepo) {
		File path = Path.fromOSString(suggestedRepo.getPath()).toFile();
		for (RepositoryMapping rm : repos) {
			IPath other = rm.getGitDirAbsolutePath();
			if (other == null) {
				continue;
			}
			if (path.equals(other.toFile())) {
				return rm;
			}
		}
		return null;
	}

