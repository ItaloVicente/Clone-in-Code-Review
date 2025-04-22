		}

		final StructuredSelection selection = new StructuredSelection(selected);
		filteredTree.getViewer().getControl().getDisplay()
				.asyncExec(() -> filteredTree.getViewer().setSelection(selection, true));
	}

	public void setDialogSettings(IDialogSettings settings) {
		this.settings = settings;
	}

	protected void storeExpandedCategories() {
		Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
		List expandedElementPaths = new ArrayList(expandedElements.length);
		for (Object expandedElement : expandedElements) {
			if (expandedElement instanceof IWizardCategory) {
				expandedElementPaths.add(((IWizardCategory) expandedElement).getPath().toString());
