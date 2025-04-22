				return hasServerConfiguration(repository,
						GitHosts.ServerType.GITHUB);
			}
			if ("hasGitlabConfiguration".equals(property)) { //$NON-NLS-1$
				return hasServerConfiguration(repository,
						GitHosts.ServerType.GITLAB);
