	public PullCommand setFastForward(
			@Nullable FastForwardMode fastForwardMode) {
		checkCallable();
		this.fastForwardMode = fastForwardMode;
		return this;
	}

