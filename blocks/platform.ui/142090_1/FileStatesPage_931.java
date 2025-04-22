		if (maxFileStates > FILE_STATES_MAXIMUM) {
			setErrorMessage(NLS.bind(IDEWorkbenchMessages.FileHistory_aboveMaxEntries, String.valueOf(FILE_STATES_MAXIMUM)));
			return FAILED_VALUE;
		}

		return maxFileStates;
	}

	private long validateMaxFileStateSize() {
		long maxFileStateSize = validateLongTextEntry(this.maxStateSizeText, MEGABYTES);
		if (maxFileStateSize == FAILED_VALUE) {
