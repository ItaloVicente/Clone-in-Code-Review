	protected boolean anyRejects() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED && cmd.getResult() != Result.OK)
				return true;
		}
		return false;
	}

	protected void failPendingCommands() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() == Result.NOT_ATTEMPTED)
				cmd.setResult(Result.REJECTED_OTHER_REASON
		}
	}

