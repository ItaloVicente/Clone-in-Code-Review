	private RemoteConfig getRemoteConfig(RepositoryTreeNode node) {
		if (node instanceof RepositoryNode)
			return SimpleConfigurePushDialog.getConfiguredRemote(node
					.getRepository());

		if (node instanceof RemoteNode || node instanceof PushNode) {
			RemoteNode remoteNode;
			if (node instanceof PushNode)
				remoteNode = (RemoteNode) node.getParent();
			else
				remoteNode = (RemoteNode) node;
