	public PullCommand setFastForward(FastForwardMode fastForwardMode) {
		checkCallable();
		this.fastForwardMode = fastForwardMode;
		return this;
	}

