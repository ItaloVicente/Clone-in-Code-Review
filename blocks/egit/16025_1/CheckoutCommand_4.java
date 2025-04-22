
	public void updateElement(UIElement element, Map parameters) {
		RepositoryTreeNode node = getSelectedNodes().get(0);
		if (node.getObject() instanceof Ref) {
			Ref ref = (Ref) node.getObject();
			if (ref.getName().startsWith(Constants.R_REMOTES)) {
				element.setText(UIText.CheckoutCommand_CheckoutLabelWithQuestion);
				return;
			}
		}
		element.setText(UIText.CheckoutCommand_CheckoutLabel);
	}
