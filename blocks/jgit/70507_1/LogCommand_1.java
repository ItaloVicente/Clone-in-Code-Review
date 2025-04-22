
	public LogCommand setMerges(boolean flag) {
		checkCallable();
		onlyMerges = flag;
		return this;
	}

	public LogCommand setNoMerges(boolean flag) {
		checkCallable();
		noMerges = flag;
		return this;
	}
