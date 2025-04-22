		placeholder.setToBeRendered(false);
	}

	private void hideNonRestorableViews() {
		List<MPlaceholder> placeholders = modelService.findElements(model, null,
				MPlaceholder.class, null);
		IViewRegistry registry = getWorkbench().getViewRegistry();
		for (MPlaceholder placeholder : placeholders) {
			IViewDescriptor descriptor = registry.find(placeholder.getElementId());
			if (descriptor != null && !descriptor.isRestorable()) {
				hideNonRestorablePlaceholder(placeholder);
			}
