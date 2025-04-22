	}

	protected void listSelectionChanged(ISelection selection) {
		setErrorMessage(null);
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object sel = ss.getFirstElement();
		if (sel instanceof WorkbenchWizardElement) {
			WorkbenchWizardElement currentWizardSelection = (WorkbenchWizardElement) sel;
			updateSelectedNode(currentWizardSelection);
		} else {
