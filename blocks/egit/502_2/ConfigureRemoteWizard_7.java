			RepositorySelectionPage sp;
			if (pushMode) {
				sp = new RepositorySelectionPage(pushMode, null,
						myConfiguration.getString(RepositoriesView.REMOTE,
								myRemoteName, RepositoriesView.PUSHURL));
			} else {
				sp = new RepositorySelectionPage(pushMode, null,
						myConfiguration.getString(RepositoriesView.REMOTE,
								myRemoteName, RepositoriesView.URL));
			}
