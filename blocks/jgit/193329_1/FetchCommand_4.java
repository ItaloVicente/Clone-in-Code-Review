
	public FetchCommand setDepth(Integer depth) {
		this.depth = depth;
		return this;
	}

	public FetchCommand setDeepenSince(OffsetDateTime deepenSince) {
		this.deepenSince = deepenSince.toInstant();
		return this;
	}

	public FetchCommand setDeepenSince(Instant deepenSince) {
		this.deepenSince = deepenSince;
		return this;
	}

	public FetchCommand addDeepenNotRef(String deepenNotRef) {
		deepenNotRefs.add(deepenNotRef);
		return this;
	}

	void setDeepenNotRefs(List<String> deepenNotRefs) {
		this.deepenNotRefs = deepenNotRefs;
	}
