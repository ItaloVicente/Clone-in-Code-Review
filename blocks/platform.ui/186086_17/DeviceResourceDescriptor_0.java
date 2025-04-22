	private final boolean shouldBeCached;

	public DeviceResourceDescriptor() {
		this(false);
	}

	protected DeviceResourceDescriptor(boolean shouldBeCached) {
		this.shouldBeCached = shouldBeCached;

	}

	final boolean shouldBeCached() {
		return shouldBeCached;
	}

