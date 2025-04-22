	private boolean isIntroView(String id) {
		return (id.equals(IIntroConstants.INTRO_VIEW_ID));
	}

	private boolean isFilteredByActivity(String elementId) {
		IViewDescriptor[] views = viewRegistry.getViews();
		for (IViewDescriptor descriptor : views) {
			if (descriptor.getId().equals(elementId) && WorkbenchActivityHelper.filterItem(descriptor)) {
				return true;
			}
		}
		return false;
	}
