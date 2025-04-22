	protected void validateInput() {
		String errorMessage = null;
		if (validator != null) {
			errorMessage = validator.isValid(text.getText());
		}
		setErrorMessage(errorMessage);
	}
