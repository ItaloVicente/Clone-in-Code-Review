	public boolean getFindBestPackRepresentation() {
		return findBestPackRepresentation;
	}

	public boolean searchForReuseTooExpensive() {
		return (System.currentTimeMillis() - searchForReuseStartTimeEpoc) / 1000 > searchForReuseMaxTimeSec;
	}

