
	@Override
	public void updateElement(UIElement element, Map parameters) {
		List<RepositoryTreeNode<?>> nodes = getSelectedNodes();
		if (nodes.size() == 1) {
			RepositoryTreeNode<?> node = nodes.get(0);
			if (node instanceof PushNode || node instanceof RemoteNode) {
			} else {
				RemoteConfig config = getRemoteConfigCached(node);
				element.setText(SimpleConfigurePushDialog
						.getSimplePushCommandLabel(config));
			}
		}
	}
