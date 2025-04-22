		return new IErrorMessageReporter() {
			@Override
			public void reportError(String errorMessage, boolean infoOnly) {
				setMessage(errorMessage);
				applyValidationResult(errorMessage, infoOnly);
			}
