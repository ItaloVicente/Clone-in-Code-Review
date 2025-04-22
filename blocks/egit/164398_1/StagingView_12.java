	private PushMode getPushMode() {
		final Repository repository = currentRepository;
		PushMode pushMode = null;
		if (repository != null) {
			pushMode = PushMode.UPSTREAM; // default mode
			try {
				if (addChangeIdAction.isChecked() && RemoteConfig
						.getAllRemoteConfigs(repository.getConfig()).stream()
						.anyMatch(GerritUtil::isGerritPush)) {
					pushMode = PushMode.GERRIT;
				}
			} catch (URISyntaxException ex) {
			}
		}
		return pushMode;
	}

