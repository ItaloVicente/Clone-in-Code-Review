		RemoteConfig config;
		try {
			config = new RemoteConfig(node.getRepository().getConfig(), remote
					.getObject());
		} catch (URISyntaxException e) {
			throw new ExecutionException(e.getMessage());
