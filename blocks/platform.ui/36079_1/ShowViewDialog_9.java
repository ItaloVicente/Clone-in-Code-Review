	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();

		String[] expandedCategoryIds = settings.getArray(STORE_EXPANDED_CATEGORIES_ID);
		if (expandedCategoryIds == null)
			return;

		if (expandedCategoryIds.length > 0)
			filteredTree.getViewer().setExpandedElements(expandedCategoryIds);

		String selectedPartId = settings.get(STORE_SELECTED_VIEW_ID);
		if (selectedPartId != null) {
			List<MPartDescriptor> descriptors = application.getDescriptors();
			for (MPartDescriptor descriptor : descriptors) {
				if (selectedPartId.equals(descriptor.getElementId())) {
					filteredTree.getViewer()
							.setSelection(new StructuredSelection(descriptor), true);
					break;
				}
			}
		}
	}
