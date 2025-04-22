	private RemoteConfig withRefSpecs(RemoteConfig config) {
		boolean fetchConfigured = !config.getFetchRefSpecs().isEmpty();
		boolean pushConfigured = !config.getPushRefSpecs().isEmpty();
		if (fetchConfigured || pushConfigured) {
			return config;
		}
		return null;
	}
