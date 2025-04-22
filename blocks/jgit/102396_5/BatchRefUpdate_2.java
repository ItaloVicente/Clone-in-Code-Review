	protected boolean isForceRefLog(ReceiveCommand cmd) {
		Boolean isForceRefLog = cmd.isForceRefLog();
		return isForceRefLog != null ? isForceRefLog.booleanValue()
				: isForceRefLog();
	}

