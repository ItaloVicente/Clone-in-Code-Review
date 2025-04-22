		return wizard.getStartingPage();
	}

	public IWizardNode getSelectedNode() {
		return selectedNode;
	}

	protected void setSelectedNode(IWizardNode node) {
		addSelectedNode(node);
		selectedNode = node;
		if (isCurrentPage()) {
