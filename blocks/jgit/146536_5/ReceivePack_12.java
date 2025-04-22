	protected boolean anyRejects() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED
					&& cmd.getResult() != Result.OK)
				return true;
		}
		return false;
