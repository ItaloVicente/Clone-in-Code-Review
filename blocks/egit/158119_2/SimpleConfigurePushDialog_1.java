	public static String getSimplePushCommandLabel(Repository repository) {
		String target = UIText.SimpleConfigurePushDialog_UpstreamTarget;
		if (repository != null) {
			RemoteConfig conf = getConfiguredRemoteCached(repository);
			if (conf != null) {
				target = conf.getName();
			}
		}
		return NLS.bind(UIText.SimpleConfigurePushDialog_PushToLabel, target);
	}

