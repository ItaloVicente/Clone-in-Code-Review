		final Repository repository = currentRepository;
		if (pushUpstream && repository != null) {
			try {
				if (withChangeId && RemoteConfig
						.getAllRemoteConfigs(repository.getConfig()).stream()
						.anyMatch(GerritUtil::isGerritPush)) {
					pushMode = PushMode.GERRIT;
				}
			} catch (URISyntaxException ex) {
			}
