
	public void setInitialLocation(File location) {
		this.locationPathField.setText(location.getAbsolutePath());
	}
	
	public void setDefault(boolean useDefaultLocation) {
		this.useDefaultsButton.setSelection(useDefaultLocation);
	}
