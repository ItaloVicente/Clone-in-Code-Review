	private void blockUntil(ProposedTimestamp ts)
			throws TimeIsUncertainException {
		List<ProposedTimestamp> times = todo.stream()
				.flatMap(p -> p.getProposedTimestamps().stream())
				.collect(Collectors.toCollection(ArrayList::new));
		times.add(ts);

		try {
			long w = getSystem().getMaxWaitForMonotonicClock(MILLISECONDS);
			ProposedTimestamp.blockUntil(times
		} catch (InterruptedException | TimeoutException e) {
			throw new TimeIsUncertainException(e);
		}
	}
