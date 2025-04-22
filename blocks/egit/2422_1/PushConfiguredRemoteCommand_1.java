		RepositoryTreeNode node = getSelectedNodes(event).get(0);
		RemoteConfig config = null;
		if (node instanceof PushNode) {
			try {
				RemoteNode remote = (RemoteNode) node.getParent();
				config = new RemoteConfig(node.getRepository().getConfig(),
						remote.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage());
			}
		} else if (node instanceof RepositoryNode) {
			config = SimpleConfigurePushDialog.getConfiguredRemote(node
					.getRepository());
