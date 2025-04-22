
	@Override
	public void updateElement(UIElement element, Map parameters) {
		RemoteConfig config = null;
		List<RepositoryTreeNode<?>> nodes = getSelectedNodes();
		if (nodes.size() == 1) {
			config = getRemoteConfigCached(nodes.get(0));
		}
		element.setText(
				SimpleConfigurePushDialog.getSimplePushCommandLabel(config));
	}
