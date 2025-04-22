			if ("hasGithubConfiguration".equals(property)) { //$NON-NLS-1$
				try {
					return GitHosts.hasGithubConfig(
							SelectionRepositoryStateCache.INSTANCE
									.getConfig(repository));
				} catch (URISyntaxException e) {
					return false;
				}
			}
