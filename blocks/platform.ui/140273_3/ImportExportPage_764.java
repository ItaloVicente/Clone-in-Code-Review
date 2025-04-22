		getContainer().showPage(getNextPage());
	}

	private void updateSelectedNode(WorkbenchWizardElement wizardElement) {
		setErrorMessage(null);
		if (wizardElement == null) {
			updateMessage();
			setSelectedNode(null);
			return;
		}

		setSelectedNode(createWizardNode(wizardElement));
		setMessage(wizardElement.getDescription());
	}

	protected void updateMessage() {
		TreeViewer viewer = getTreeViewer();
		if (viewer != null) {
			ISelection selection = viewer.getSelection();
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object sel = ss.getFirstElement();
			if (sel instanceof WorkbenchWizardElement) {
				updateSelectedNode((WorkbenchWizardElement) sel);
			} else {
				setSelectedNode(null);
			}
		} else {
