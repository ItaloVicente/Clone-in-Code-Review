		if (node instanceof RepositoryNode) {
			return SimpleConfigureFetchDialog
					.getConfiguredRemote(node.getRepository());
		}
		if (node instanceof FetchNode) {
			node = node.getParent();
		}
		if (node instanceof RemoteNode) {
