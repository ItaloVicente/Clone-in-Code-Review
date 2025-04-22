	public boolean isFirstParent() {
		return firstParent;
	}

	public void setFirstParent(final boolean enable) {
		assertNotStarted();
		firstParent = enable;
		queue = new DateRevQueue(firstParent);
		pending = new StartGenerator(this);
	}

