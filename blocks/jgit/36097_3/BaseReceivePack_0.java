	protected boolean anyRejects() {
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED || cmd.getResult() != Result.OK)
				return false;
		}
		return true;
	}

