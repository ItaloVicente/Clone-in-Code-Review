
	private static void reject(List<Command> todo, String msg) {
		for (Command c : todo) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON, msg);
				msg = JGitText.get().transactionAborted;
			}
		}
	}
