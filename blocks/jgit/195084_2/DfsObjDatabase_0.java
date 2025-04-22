	@Override
	public Set<ObjectId> getShallowCommits() throws IOException {
		return shallowCommits;
	}

	@Override
	public void setShallowCommits(Set<ObjectId> shallowCommits) {
		if (!shallowCommits.isEmpty()) {
			throw new UnsupportedOperationException(
					"Shallow commits expected to be empty.");
		}
	}

