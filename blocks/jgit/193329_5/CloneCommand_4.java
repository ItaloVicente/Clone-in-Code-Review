	public CloneCommand setDepth(Integer depth) {
		if (depth != null && depth < 1) {
			throw new IllegalArgumentException("depth must be >= 1");
		}
		this.depth = depth;
		return this;
	}

	public CloneCommand setShallowSince(OffsetDateTime shallowSince) {
		this.shallowSince = shallowSince.toInstant();
		return this;
	}

	public CloneCommand setShallowSince(Instant shallowSince) {
		this.shallowSince = shallowSince;
		return this;
	}

	public CloneCommand addShallowExclude(String shallowExclude) {
		shallowExcludes.add(shallowExclude);
		return this;
	}

	public CloneCommand addShallowExclude(ObjectId shallowExclude) {
		shallowExcludes.add(shallowExclude.name());
		return this;
	}

