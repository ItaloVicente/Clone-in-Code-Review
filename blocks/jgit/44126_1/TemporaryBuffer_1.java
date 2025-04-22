		this(limit
	}

	protected TemporaryBuffer(final int limit
		if (estimatedSize > limit)
			throw new IllegalArgumentException();
		this.inCoreLimit = limit;
		this.initialBlocks = (estimatedSize - 1) / Block.SZ + 1;
