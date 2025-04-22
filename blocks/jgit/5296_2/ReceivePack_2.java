	public boolean isSideBand() throws RequestNotYetReadException {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
		return enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
	}

