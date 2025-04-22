		}

		viewer.setSelection(new StructuredSelection(selected), true);
	}

	protected void storeExpandedCategories(String setting, TreeViewer viewer) {
		Object[] expandedElements = viewer.getExpandedElements();
		List expandedElementPaths = new ArrayList(expandedElements.length);
		for (Object expandedElement : expandedElements) {
			if (expandedElement instanceof IWizardCategory) {
				expandedElementPaths.add(((IWizardCategory) expandedElement).getPath().toString());
