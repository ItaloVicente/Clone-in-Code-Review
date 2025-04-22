	public CloneCommand setDepth(int depth) {
		if (depth < 1) {
			throw new IllegalArgumentException(JGitText.get().depthMustBeAt1);
		}
		this.depth = Integer.valueOf(depth);
		return this;
	}

	public CloneCommand setShallowSince(@NonNull OffsetDateTime shallowSince) {
		this.shallowSince = shallowSince.toInstant();
		return this;
	}

	public CloneCommand setShallowSince(@NonNull Instant shallowSince) {
		this.shallowSince = shallowSince;
		return this;
	}

	public CloneCommand addShallowExclude(@NonNull String shallowExclude) {
		shallowExcludes.add(shallowExclude);
		return this;
	}

	public CloneCommand addShallowExclude(@NonNull ObjectId shallowExclude) {
		shallowExcludes.add(shallowExclude.name());
		return this;
	}

