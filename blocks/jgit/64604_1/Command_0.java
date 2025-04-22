	public static void abort(Iterable<Command> commands
		if (why == null || why.isEmpty()) {
			why = JGitText.get().transactionAborted;
		}
		for (Command c : commands) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
				why = JGitText.get().transactionAborted;
			}
		}
	}

