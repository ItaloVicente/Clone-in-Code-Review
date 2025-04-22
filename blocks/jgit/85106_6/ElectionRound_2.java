
	private void blockUntil(ProposedTimestamp ts) throws IOException {
		try {
			long w = getSystem().getMaxWaitForMonotonicClock(MICROSECONDS);
			ts.blockUntil(w
		} catch (InterruptedException | TimeoutException e) {
			throw new TimeIsUncertainException(e);
		}
	}
