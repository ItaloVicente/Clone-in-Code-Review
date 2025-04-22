	private static boolean hasGerritConfiguration(Repository repository) {
		Config config = repository.getConfig();
		if (GerritUtil.getCreateChangeId(config))
			return true;
		try {
			List<RemoteConfig> remoteConfigs = RemoteConfig.getAllRemoteConfigs(config);
			for (RemoteConfig remoteConfig : remoteConfigs) {
				for (RefSpec pushSpec : remoteConfig.getPushRefSpecs()) {
					boolean gerritPushRef = pushSpec.getDestination().startsWith(
							GerritUtil.REFS_FOR);
					if (gerritPushRef)
						return true;
				}
			}
		} catch (URISyntaxException e) {
			return false;
		}
		return false;
	}

