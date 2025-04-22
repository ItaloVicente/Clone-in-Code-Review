	public PushMode getPushMode() {
		if (!pushRequested) {
			return null;
		}
		return isGerritRepo && getCreateChangeId() ? PushMode.GERRIT
				: PushMode.UPSTREAM;
	}

	private boolean isGerritRepo() {
		try {
			return RemoteConfig.getAllRemoteConfigs(repository.getConfig())
					.stream().anyMatch(GerritUtil::isGerritPush);
		} catch (Exception e) {
			Activator.handleError(
					MessageFormat
							.format(UIText.CommitDialog_InvalidConfig,
									RepositoryUtil.INSTANCE
											.getRepositoryName(repository)),
					e, true);
		}
		return false;
