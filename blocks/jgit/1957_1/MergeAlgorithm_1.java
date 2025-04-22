	public MergeAlgorithm() {
		this(MyersDiff.INSTANCE);
	}

	public MergeAlgorithm(DiffAlgorithm diff) {
		this.diffAlg = diff;
