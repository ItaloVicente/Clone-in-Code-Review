	private boolean resolveTimestamps() {
		try {
			long deadline = System.currentTimeMillis() + 5000;
			for (ProposedTimestamp ts : getProposedTimestamps()) {
				long wait = deadline - System.currentTimeMillis();
				ts.blockUntil(wait
			}
			return true;
		} catch (TimeoutException | InterruptedException e) {
			String msg = JGitText.get().timeIsUncertain;
			for (ReceiveCommand c : commands) {
				if (c.getResult() == NOT_ATTEMPTED) {
					c.setResult(REJECTED_OTHER_REASON
				}
			}
			return false;
		}
	}

