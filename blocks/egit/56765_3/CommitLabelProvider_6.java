	private void setShowEmailAddresses(boolean showEmail) {
		if (showEmail != this.showEmail) {
			this.showEmail = showEmail;
			fireLabelProviderChanged(new LabelProviderChangedEvent(this));
		}
	}

	private void setDateFormatter(GitDateFormatter formatter) {
		dateFormatter.set(formatter);
		fireLabelProviderChanged(new LabelProviderChangedEvent(this));
