	private RemoteConfig getRemoteConfigCached(RepositoryTreeNode node) {
		if (node instanceof RepositoryNode) {
			return SimpleConfigurePushDialog
					.getConfiguredRemoteCached(node.getRepository());
		}
		if (node instanceof PushNode) {
			node = node.getParent();
		}
		if (node instanceof RemoteNode) {
			try {
				RemoteNode remoteNode = (RemoteNode) node;
				RemoteConfig config = new RemoteConfig(
						RepositoryStateCache.INSTANCE
								.getConfig(node.getRepository()),
						remoteNode.getObject());
				return withRefSpecs(config);
			} catch (URISyntaxException e) {
				return null;
			}
		}
