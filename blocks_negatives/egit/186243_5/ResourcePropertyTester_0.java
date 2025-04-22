				try {
					return GitHosts.hasServerConfig(
							SelectionRepositoryStateCache.INSTANCE
									.getConfig(repository),
							GitHosts.ServerType.GITHUB);
				} catch (URISyntaxException e) {
					return false;
				}
