	public int getDeltaSearchWindowSize() {
		return deltaSearchWindowSize;
	}

	public void setDeltaSearchWindowSize(int objectCount) {
		if (objectCount <= 2)
			setDeltaCompress(false);
		else
			deltaSearchWindowSize = objectCount;
	}

