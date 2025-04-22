	public ValidationHook getValidationHook() {
		return validator;
	}

	public void setValidationHook(final ValidationHook v) {
		validator = v != null ? v : ValidationHook.NULL;
	}

