	private boolean isStickyView(String elementId) {
		IStickyViewDescriptor[] stickyViews = PlatformUI.getWorkbench().getViewRegistry().getStickyViews();
		for (IStickyViewDescriptor stickyViewDescriptor : stickyViews) {
			if (stickyViewDescriptor.getId().equals(elementId)) {
				return true;
			}
		}
		return false;
	}

