	@Override
	public boolean isEnabled() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isEnabled();
		}
		return super.isEnabled();
	}

	@Override
	public boolean isGroupMarker() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isGroupMarker();
		}
		return super.isGroupMarker();
	}

	@Override
	public boolean isSeparator() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isSeparator();
		}
		return super.isSeparator();
	}

	@Override
	public boolean isVisible() {
		if (loadedDynamicContribution != null) {
			return loadedDynamicContribution.isVisible();
		}
		return super.isVisible();
	}

	@Override
	public void saveWidgetState() {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.saveWidgetState();
		}
		super.saveWidgetState();
	}

	@Override
	public void setVisible(boolean visible) {
		if (loadedDynamicContribution != null) {
			loadedDynamicContribution.setVisible(visible);
		}
		super.setVisible(visible);
	}

