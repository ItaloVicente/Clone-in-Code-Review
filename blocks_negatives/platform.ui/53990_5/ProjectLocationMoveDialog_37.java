		return new IErrorMessageReporter(){
			@Override
			public void reportError(String errorMessage, boolean notError) {
				setMessage(errorMessage);

			}
		};
