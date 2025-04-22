	FetchRequest(Set<ObjectId> wantIds, int depth,
			Set<ObjectId> clientShallowCommits, long filterBlobLimit,
			Set<String> clientCapabilities) {
		if (wantIds == null || clientShallowCommits == null
				|| clientCapabilities == null) {
			throw new NullPointerException();
		}

