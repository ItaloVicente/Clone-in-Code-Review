	private static boolean abort(Iterable<Command> cmdList) {
		for (Command cmd : cmdList) {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				reject(cmd, JGitText.get().transactionAborted);
			}
		}
		return false;
	}

	private static void reject(Command cmd, String msg) {
		cmd.setResult(REJECTED_OTHER_REASON, msg);
	}

