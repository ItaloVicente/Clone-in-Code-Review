	public static void abort(Iterable<ReceiveCommand> commands) {
		for (ReceiveCommand c : commands) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
						JGitText.get().transactionAborted);
			}
		}
	}

