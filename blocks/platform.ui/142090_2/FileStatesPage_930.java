			return FAILED_VALUE;
		}

		if (value <= 0) {
			setErrorMessage(IDEWorkbenchMessages.FileHistory_mustBePositive);
			return FAILED_VALUE;
		}

		return value;
	}

	private int validateMaxFileStates() {
		int maxFileStates = validateIntegerTextEntry(this.maxStatesText);
		if (maxFileStates == FAILED_VALUE) {
