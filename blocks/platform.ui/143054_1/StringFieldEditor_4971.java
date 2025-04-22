	}

	public void setValidateStrategy(int value) {
		Assert.isTrue(value == VALIDATE_ON_FOCUS_LOST
				|| value == VALIDATE_ON_KEY_STROKE);
		validateStrategy = value;
	}

	public void showErrorMessage() {
		showErrorMessage(errorMessage);
	}

	protected void valueChanged() {
		setPresentsDefaultValue(false);
		boolean oldState = isValid;
		refreshValidState();

		if (isValid != oldState) {
