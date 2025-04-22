	protected boolean blockUntilTimestamps(long maxWait
		if (timestamps == null) {
			return true;
		}
		try {
			ProposedTimestamp.blockUntil(timestamps
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

