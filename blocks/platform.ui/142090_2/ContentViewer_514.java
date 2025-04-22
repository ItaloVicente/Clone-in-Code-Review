	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		IBaseLabelProvider oldProvider = this.labelProvider;
		if (labelProvider == oldProvider) {
			return;
		}
		if (oldProvider != null) {
			oldProvider.removeListener(this.labelProviderListener);
		}
		this.labelProvider = labelProvider;
		if (labelProvider != null) {
			labelProvider.addListener(this.labelProviderListener);
		}
		refresh();
