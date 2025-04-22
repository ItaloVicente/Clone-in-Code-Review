	public boolean isFirstParent() {
		return firstParent;
	}

	public void setFirstParent(boolean enable) {
		assertNotStarted();
		queue = new DateRevQueue(firstParent = enable);
		pending = new StartGenerator(this);
	}

