		RepositoryTreeNode node = getSelectedNodes(event).get(0);
		RemoteConfig config = null;
		if (node instanceof FetchNode) {
			try {
				RemoteNode remote = (RemoteNode) node.getParent();
				config = new RemoteConfig(node.getRepository().getConfig(),
						remote.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage());
			}
		} else if (node instanceof RepositoryNode) {
			config = SimpleConfigureFetchDialog.getConfiguredRemote(node
					.getRepository());
		}
		if (config == null) {
			MessageDialog
					.openInformation(
							getShell(event),
							UIText.SimpleFetchActionHandler_NothingToFetchDialogTitle,
							UIText.SimpleFetchActionHandler_NothingToFetchDialogMessage);
			return null;
