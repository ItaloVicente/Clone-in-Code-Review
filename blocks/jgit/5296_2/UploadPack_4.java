	public boolean isSideBand() throws RequestNotYetReadException {
		if (options == null)
			throw new RequestNotYetReadException();
		return (options.contains(OPTION_SIDE_BAND)
				|| options.contains(OPTION_SIDE_BAND_64K));
	}

