	private void reorderControls(Object[] toShowJobElements) {
		int limit = Math.min(toShowJobElements.length, getMaxDisplayed());
		if (limit == 0) {
			for (Control existing : jobItemControls.values()) {
				existing.dispose();
			}
			jobItemControls.clear();
			return;
