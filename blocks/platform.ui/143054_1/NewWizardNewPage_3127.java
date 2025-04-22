		return false;
	}

	private void updateWizardSelection(IWizardDescriptor selectedObject) {
		selectedElement = selectedObject;
		WorkbenchWizardNode selectedNode;
		if (selectedWizards.containsKey(selectedObject)) {
			selectedNode = (WorkbenchWizardNode) selectedWizards.get(selectedObject);
		} else {
			selectedNode = new WorkbenchWizardNode(page, selectedObject) {
				@Override
