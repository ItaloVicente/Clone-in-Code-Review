	private List<RemoteConfig> getUsableConfigs(final List<RemoteConfig> remotes) {

		if (remotes == null)
			return null;

		List<RemoteConfig> result = new ArrayList<RemoteConfig>();

		for (RemoteConfig config : remotes)
			if ((sourceSelection && !config.getURIs().isEmpty() || !sourceSelection
					&& !config.getPushURIs().isEmpty()))
				result.add(config);

		if (!result.isEmpty())
			return result;

		return null;
