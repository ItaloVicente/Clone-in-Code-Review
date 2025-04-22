
	public FetchCommand setDepth(int depth) {
		if (depth < 1) {
			throw new IllegalArgumentException(JGitText.get().depthMustBeAt1);
		}
		this.depth = depth;
		return this;
	}

	public FetchCommand setShallowSince(@NonNull OffsetDateTime shallowSince) {
		this.deepenSince = shallowSince.toInstant();
		return this;
	}

	public FetchCommand setShallowSince(@NonNull Instant shallowSince) {
		this.deepenSince = shallowSince;
		return this;
	}

	public FetchCommand addShallowExclude(@NonNull String shallowExclude) {
		shallowExcludes.add(shallowExclude);
		return this;
	}

	public FetchCommand addShallowExclude(@NonNull ObjectId shallowExclude) {
		shallowExcludes.add(shallowExclude.name());
		return this;
	}

	public FetchCommand setUnshallow(boolean unshallow) {
		this.unshallow = unshallow;
		return this;
	}

	void setShallowExcludes(List<String> shallowExcludes) {
		this.shallowExcludes = shallowExcludes;
	}
