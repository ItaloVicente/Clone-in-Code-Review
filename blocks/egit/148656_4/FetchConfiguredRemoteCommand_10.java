	private RemoteConfig getRemoteConfigCached(RepositoryTreeNode node)
			throws ExecutionException {
		if (node instanceof RepositoryNode) {
			return SimpleConfigureFetchDialog
					.getConfiguredRemoteCached(node.getRepository());
		}
		if (node instanceof FetchNode) {
			node = node.getParent();
		}
		if (node instanceof RemoteNode) {
