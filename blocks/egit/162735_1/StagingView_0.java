		final Repository repository = currentRepository;
		if (pushUpstream && repository != null) {
			pushMode = PushMode.UPSTREAM; // default mode
			try {
				if (pushUpstream && withChangeId
						&& RemoteConfig
								.getAllRemoteConfigs(repository.getConfig())
								.stream().anyMatch(GerritUtil::isGerritPush)) {
					pushMode = PushMode.GERRIT;
				}
			} catch (URISyntaxException ex) {
			}
