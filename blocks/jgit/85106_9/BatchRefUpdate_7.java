	public List<ProposedTimestamp> getProposedTimestamps() {
		if (timestamps != null) {
			return Collections.unmodifiableList(timestamps);
		}
		return Collections.emptyList();
	}

	public BatchRefUpdate addProposedTimestamp(ProposedTimestamp ts) {
		if (timestamps == null) {
			timestamps = new ArrayList<>(4);
		}
		timestamps.add(ts);
		return this;
	}

