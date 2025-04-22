	public void setShallowCommits(Set<ObjectId> shallowCommits)
			throws IOException {
		if (!shallowCommits.isEmpty()) {
			throw new UnsupportedOperationException("Shallow commits expected to be empty.");
		}
	}
