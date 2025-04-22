	private RemoteAndMerge extractRemoteAndMergeForDst(String rev) {
		try {
			List<RemoteConfig> remoteConfigs = RemoteConfig
					.getAllRemoteConfigs(repo.getConfig());
			for (RemoteConfig remoteConfig : remoteConfigs) {
				List<RefSpec> fetchRefSpecs = remoteConfig.getFetchRefSpecs();
				for (RefSpec fetchRefSpec : fetchRefSpecs) {
					if (fetchRefSpec.matchDestination(rev)) {
						RefSpec expanded = fetchRefSpec
								.expandFromDestination(rev);
						return new RemoteAndMerge(remoteConfig.getName(),
								expanded.getSource());
					}
				}
