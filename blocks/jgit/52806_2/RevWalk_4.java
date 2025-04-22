	public boolean isFirstParent() {
		return firstParent;
	}

	public void setFirstParent(boolean enable) {
		assertNotStarted();
		assertNoCommitsMarkedStart();
		firstParent = enable;
		queue = new DateRevQueue(firstParent);
		pending = new StartGenerator(this);
	}

