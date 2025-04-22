	private void blockUntil(ProposedTimestamp ts)
			throws TimeIsUncertainException {
		List<ProposedTimestamp> times = todo.stream()
				.flatMap(p -> p.getProposedTimestamps().stream())
				.collect(Collectors.toCollection(ArrayList::new));
		times.add(ts);

		try {
			Duration maxWait = getSystem().getMaxWaitForMonotonicClock();
			ProposedTimestamp.blockUntil(times
		} catch (InterruptedException | TimeoutException e) {
			throw new TimeIsUncertainException(e);
		}
	}
