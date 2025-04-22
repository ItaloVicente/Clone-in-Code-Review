	@Override
	public void handleLine(String line) {
		assert getState() == OperationState.READING
			: "Read ``" + line + "'' when in " + getState() + " state";
		getCallback().receivedStatus(matchStatus(line, STORED));
		transitionState(OperationState.COMPLETE);
	}
