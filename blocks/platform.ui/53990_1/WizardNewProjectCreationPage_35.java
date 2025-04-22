		return (errorMessage, infoOnly) -> {
			if (infoOnly) {
				setMessage(errorMessage, IStatus.INFO);
				setErrorMessage(null);
			}
			else
				setErrorMessage(errorMessage);
			boolean valid = errorMessage == null;
			if(valid) {
				valid = validatePage();
