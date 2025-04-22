		Repository repository = getRepository();
		RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemoteCached(repository);
		if (config == null) {
			return false;
		}
		List<RefSpec> refSpecs = config.getPushRefSpecs();
		if (!refSpecs.isEmpty()) {
			return true;
		}
		PushDefault pushDefault = SelectionRepositoryStateCache.INSTANCE
				.getConfig(repository).get(PushConfig::new).getPushDefault();
		return !PushDefault.NOTHING.equals(pushDefault);
