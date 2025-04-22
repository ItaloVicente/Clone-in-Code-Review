
	public MergeCommand setFastForward(FastForwardMode fastForwardMode) {
		checkCallable();
		this.fastForwardMode = fastForwardMode;
		return this;
	}
