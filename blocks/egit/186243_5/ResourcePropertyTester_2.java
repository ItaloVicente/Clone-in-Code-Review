	private static boolean hasServerConfiguration(Repository repository,
			GitHosts.ServerType server) {
		try {
			return GitHosts
					.hasServerConfig(SelectionRepositoryStateCache.INSTANCE
							.getConfig(repository), server);
		} catch (URISyntaxException e) {
			return false;
		}
	}

