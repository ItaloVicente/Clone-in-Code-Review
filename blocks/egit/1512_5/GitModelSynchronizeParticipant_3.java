				if (addGitProvider
						&& providerId.equals(GitChangeSetModelProvider.ID))
					addGitProvider = false;
			}

			if (addGitProvider)
				providers.add(GitChangeSetModelProvider.getProvider());
