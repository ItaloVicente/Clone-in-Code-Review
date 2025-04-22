		final List<RemoteConfig> remotes = RemoteConfig
				.getAllRemoteConfigs(localDb.getConfig());
		repoPage = new RepositorySelectionPage(true, remotes, null);
		refSpecPage = new RefSpecPage(localDb, false) {
			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					setSelection(repoPage.getSelection());
					setCredentials(repoPage.getCredentials());
				}
				super.setVisible(visible);
			}
		};
