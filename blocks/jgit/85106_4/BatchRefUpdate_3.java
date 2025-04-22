	protected boolean blockUntilTimestamps(long maxWait
		if (timestamps == null || timestamps.isEmpty()) {
			return true;
		}
		try {
			long deadline = System.nanoTime() + unit.toNanos(maxWait);
			for (ProposedTimestamp ts : getProposedTimestamps()) {
				long w = deadline - System.nanoTime();
				if (w < 0) {
					throw new TimeoutException();
				}
				ts.blockUntil(w
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

