
	@Override
	public void updateElement(UIElement element, Map parameters) {
		Repository repo = null;
		List<RepositoryTreeNode<?>> nodes = getSelectedNodes();
		if (nodes.size() == 1) {
			repo = nodes.get(0).getRepository();
		}
		element.setText(
				SimpleConfigurePushDialog.getSimplePushCommandLabel(repo));
	}
