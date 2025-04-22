
	public FetchCommand setDepth(Integer depth) {
		if (depth != null && depth < 1) {
			throw new IllegalArgumentException("depth must be >= 1");
		}
		this.depth = depth;
		return this;
	}

	public FetchCommand setShallowSince(OffsetDateTime shallowSince) {
		this.deepenSince = shallowSince.toInstant();
		return this;
	}

	public FetchCommand setShallowSince(Instant shallowSince) {
		this.deepenSince = shallowSince;
		return this;
	}

	public FetchCommand addShallowExclude(String shallowExclude) {
		shallowExcludes.add(shallowExclude);
		return this;
	}

	public FetchCommand addShallowExclude(ObjectId shallowExclude) {
		shallowExcludes.add(shallowExclude.name());
		return this;
	}

	void setShallowExcludes(List<String> shallowExcludes) {
		this.shallowExcludes = shallowExcludes;
	}
