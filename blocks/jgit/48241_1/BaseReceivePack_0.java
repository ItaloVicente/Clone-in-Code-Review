	public boolean isQuiet() throws RequestNotYetReadException {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
		return quiet;
	}

