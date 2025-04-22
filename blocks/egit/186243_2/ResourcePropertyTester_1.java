			if ("hasGitlabConfiguration".equals(property)) { //$NON-NLS-1$
				try {
					return GitHosts.hasServerConfig(
							SelectionRepositoryStateCache.INSTANCE
									.getConfig(repository),
							GitHosts.ServerType.GITLAB);
				} catch (URISyntaxException e) {
					return false;
				}
			}
