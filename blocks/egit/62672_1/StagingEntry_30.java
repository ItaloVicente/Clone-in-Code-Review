	public boolean isMissing() {
		return state == State.MISSING || state == State.MISSING_AND_CHANGED;
	}

	@Override
	public StagingState getStagingState() {
