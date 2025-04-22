
	@Override
	public void updateElement(UIElement element, Map parameters) {
		List<RepositoryTreeNode> nodes = getSelectedNodes();
		if (nodes.size() == 1) {
			RepositoryTreeNode node = nodes.get(0);
			if (node instanceof FetchNode || node instanceof RemoteNode) {
			} else {
				try {
					RemoteConfig config = getRemoteConfigCached(node);
					if (config != null) {
						element.setText(SimpleConfigureFetchDialog
								.getSimpleFetchCommandLabel(config));
					}
				} catch (ExecutionException e) {
				}
			}
		}
	}
