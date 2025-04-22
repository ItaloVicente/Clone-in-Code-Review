	public void setRefLogMessage(String msg
		customRefLog = true;
		if (msg == null && !appendStatus) {
			disableRefLog();
		} else if (msg == null && appendStatus) {
			refLogIncludeResult = true;
		} else {
			refLogMessage = msg;
			refLogIncludeResult = appendStatus;
		}
	}

	public void disableRefLog() {
		customRefLog = true;
		refLogMessage = null;
		refLogIncludeResult = false;
	}

	public boolean hasCustomRefLog() {
		return customRefLog;
	}

	public boolean isRefLogDisabled() {
		return refLogMessage == null;
	}

	@Nullable
	public String getRefLogMessage() {
		return refLogMessage;
	}

	public boolean isRefLogIncludingResult() {
		return refLogIncludeResult;
	}

