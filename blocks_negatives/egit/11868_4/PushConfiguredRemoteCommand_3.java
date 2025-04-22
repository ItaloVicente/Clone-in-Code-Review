	private RemoteConfig getRemoteConfig(RepositoryTreeNode node)
			throws ExecutionException {
		if (node instanceof PushNode)
			try {
				RemoteNode remote = (RemoteNode) node.getParent();
				return new RemoteConfig(node.getRepository().getConfig(),
						remote.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage());
			}
