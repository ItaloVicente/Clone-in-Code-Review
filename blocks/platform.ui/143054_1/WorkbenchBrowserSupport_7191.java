	public void setDesiredBrowserSupportId(String desiredBrowserSupportId) {
		dispose(); // prep for a new help system
		this.desiredBrowserSupportId = desiredBrowserSupportId;
	}

	protected void dispose() {
		activeSupport = null;
		initialized = false;
	}
