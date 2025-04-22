	@Override
	public boolean canFinish() {
		if (getContainer().getCurrentPage() == repoPage) {
			RepositorySelection sel = repoPage.getSelection();
			if (sel.isConfigSelected()) {
				RemoteConfig config = sel.getConfig();
				return !config.getURIs().isEmpty()
						&& !config.getFetchRefSpecs().isEmpty();
			}
		}
		return super.canFinish();
	}

