
	public void updateElement(UIElement element, Map parameters) {
		List<RepositoryTreeNode> nodes = getSelectedNodes();
		if (!nodes.isEmpty()) {
			RepositoryTreeNode node = nodes.get(0);
			if (node.getObject() instanceof Ref) {
				Ref ref = (Ref) node.getObject();
				if (BranchOperationUI.checkoutWillShowQuestionDialog(ref
						.getName())) {
					element.setText(UIText.CheckoutCommand_CheckoutLabelWithQuestion);
					return;
				}
			}
		}
		element.setText(UIText.CheckoutCommand_CheckoutLabel);
	}
