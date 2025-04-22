	protected boolean changeVisibility(boolean visible) {
		if (visible != getVisible()) {
			setVisible(visible);
			GridData data = (GridData) getLayoutData();
			data.exclude = !visible;
			return true;
		}
		return false;
