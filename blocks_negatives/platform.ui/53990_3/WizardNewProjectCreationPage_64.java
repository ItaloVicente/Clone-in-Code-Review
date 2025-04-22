		return new IErrorMessageReporter(){
			@Override
			public void reportError(String errorMessage, boolean infoOnly) {
				if (infoOnly) {
					setMessage(errorMessage, IStatus.INFO);
					setErrorMessage(null);
				}
				else
					setErrorMessage(errorMessage);
				boolean valid = errorMessage == null;
				if(valid) {
					valid = validatePage();
				}

				setPageComplete(valid);
