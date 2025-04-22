	public static boolean isEnabledForState(Repository repo,
			RepositoryState state) {
		return state == RepositoryState.SAFE && hasHead(repo);
	}

	private static boolean hasHead(Repository repo) {
