	}

	public void setPageInput(IAdaptable input) {
		pageInput = input;
	}

	public void setReplaceEnabled(boolean isEnabled) {
		if (replaceEnabled != isEnabled) {
			replaceEnabled = isEnabled;
			if (canRun() && parentMenuManager != null) {
