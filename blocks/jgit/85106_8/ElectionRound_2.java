
	private void blockUntil(ProposedTimestamp ts) throws IOException {
		try {
			ts.blockUntil(getSystem().getMaxWaitForMonotonicClock());
		} catch (InterruptedException | TimeoutException e) {
			throw new TimeIsUncertainException(e);
		}
	}
