	protected void addToFocusTracking(@NonNull Control control) {
		focusTracker.addToFocusTracking(control);
	}

	private void addSectionTextToFocusTracking(@NonNull Section composite) {
		for (Control control : composite.getChildren()) {
			if (control instanceof AbstractHyperlink) {
				addToFocusTracking(control);
			}
		}
	}

