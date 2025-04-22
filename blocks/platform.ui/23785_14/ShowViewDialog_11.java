	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();

		Object[] expandedElements = filteredTree.getViewer().getExpandedElements();
		String[] expandedCategoryIds = new String[expandedElements.length];
		for (int i = 0; i < expandedElements.length; ++i) {
			if (expandedElements[i] instanceof MPartDescriptor)
				expandedCategoryIds[i] = ((MPartDescriptor) expandedElements[i]).getElementId();
			else
				expandedCategoryIds[i] = expandedElements[i].toString();
		}
