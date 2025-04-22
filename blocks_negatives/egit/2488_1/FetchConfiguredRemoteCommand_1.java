		FetchNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();

		RemoteConfig config;
		try {
			config = new RemoteConfig(node.getRepository().getConfig(), remote
					.getObject());
		} catch (URISyntaxException e) {
			throw new ExecutionException(e.getMessage());
