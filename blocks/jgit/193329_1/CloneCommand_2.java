	public CloneCommand setDepth(Integer depth) {
		this.depth = depth;
		return this;
	}

	public CloneCommand setDeepenSince(OffsetDateTime deepenSince) {
		this.deepenSince = deepenSince.toInstant();
		return this;
	}

	public CloneCommand setDeepenSince(Instant deepenSince) {
		this.deepenSince = deepenSince;
		return this;
	}

	public CloneCommand addDeepenNotRef(String deepenNotRef) {
		deepenNotRefs.add(deepenNotRef);
		return this;
	}

