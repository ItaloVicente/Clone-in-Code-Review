			return FAILED_VALUE;
		}

		if (value <= 0) {
			setErrorMessage(IDEWorkbenchMessages.FileHistory_mustBePositive);
			return FAILED_VALUE;
		}

		return value;
	}

	private long validateLongTextEntry(Text text, long scale) {

		long value;

		try {
			String string = text.getText();
