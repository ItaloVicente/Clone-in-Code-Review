	public void setPreferenceStore(IPreferenceStore store) {
		preferenceStore = store;
	}

	protected void setPresentsDefaultValue(boolean booleanValue) {
		isDefaultPresented = booleanValue;
	}

	public void setPropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListener = listener;
	}

	protected void showErrorMessage(String msg) {
		if (page != null) {
