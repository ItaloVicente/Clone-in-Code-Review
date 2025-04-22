	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == repoPage) {
			RepositorySelection sel = repoPage.getSelection();
			if (sel.isConfigSelected()) {
				RemoteConfig config = sel.getConfig();
				return !config.getPushURIs().isEmpty()
						|| !config.getURIs().isEmpty();
			}
		}
		return super.canFinish();
	}

