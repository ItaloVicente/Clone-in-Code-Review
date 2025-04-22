	private void setErrorMessage(String message) {
		StackLayout layout = (StackLayout) getControl().getParent().getLayout();
		if (message != null) {
			errorText.setText(message);
			layout.topControl = errorText;
		} else {
			layout.topControl = getControl();
		}
		getControl().getParent().layout();
