	public boolean isAtomic() {
		return atomic;
	}

	public PushCommand setAtomic(boolean atomic) {
		checkCallable();
		this.atomic = atomic;
		return this;
	}

