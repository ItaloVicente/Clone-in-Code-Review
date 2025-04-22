
		this.wantIds = wantIds;
		this.depth = depth;
		this.clientShallowCommits = clientShallowCommits;
		this.filterBlobLimit = filterBlobLimit;
		this.options = options;
	}

	Set<ObjectId> getWantIds() {
		return wantIds;
	}

	int getDepth() {
		return depth;
	}

	Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	long getFilterBlobLimit() {
		return filterBlobLimit;
	}

	Set<String> getOptions() {
		return options;
