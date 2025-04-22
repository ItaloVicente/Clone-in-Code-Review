	protected boolean isRefLogDisabled(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog() ? cmd.isRefLogDisabled() : isRefLogDisabled();
	}

	protected String getRefLogMessage(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog() ? cmd.getRefLogMessage() : getRefLogMessage();
	}

	protected boolean isRefLogIncludingResult(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog()
				? cmd.isRefLogIncludingResult() : isRefLogIncludingResult();
	}

