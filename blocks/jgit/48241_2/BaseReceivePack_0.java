	public boolean isAllowQuiet() {
		return allowQuiet;
	}

	public void setAllowQuiet(boolean allow) {
		allowQuiet = allow;
	}

	public boolean isQuiet() throws RequestNotYetReadException {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
		return quiet;
	}

