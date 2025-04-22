	private void reject() {
		String aborted = JGitText.get().transactionAborted;
		for (ReceiveCommand c : getCommands()) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON, aborted);
			}
		}
	}

