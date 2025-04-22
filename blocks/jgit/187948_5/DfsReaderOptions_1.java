	public boolean shouldLoadRevIndexInParallel() {
		return loadRevIndexInParallel;
	}

	public DfsReaderOptions setLoadRevIndexInParallel(
			boolean loadRevIndexInParallel) {
		this.loadRevIndexInParallel = loadRevIndexInParallel;
		return this;
	}

