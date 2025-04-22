		this(limit
	}

	protected TemporaryBuffer(final int estimatedSize
		if (estimatedSize > limit)
			throw new IllegalArgumentException();
		this.inCoreLimit = limit;
		this.initialBlocks = (estimatedSize - 1) / Block.SZ + 1;
