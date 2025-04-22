
	public void setInitialLocation(File location) {
		this.locationPathField.setText(location.getAbsolutePath());
	}
	
	public void setDefault(boolean useDefaultLocation) {
		this.useDefaultsButton.setSelection(useDefaultLocation);
	}
	
	public void setEnabled(boolean enabled) {
		this.useDefaultsButton.setEnabled(this.useDefaultsButton.isEnabled() && enabled);
		this.locationPathField.setEnabled(this.locationPathField.isEnabled() && enabled);
		this.browseButton.setEnabled(this.browseButton.isEnabled() && enabled);
	}
 }
