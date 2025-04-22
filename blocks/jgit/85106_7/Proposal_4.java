	public List<ProposedTimestamp> getProposedTimestamps() {
		if (timestamps != null) {
			return timestamps;
		}
		return Collections.emptyList();
	}

	public Proposal addProposedTimestamp(ProposedTimestamp ts) {
		if (timestamps == null) {
			timestamps = new ArrayList<>(4);
		}
		timestamps.add(ts);
		return this;
	}

