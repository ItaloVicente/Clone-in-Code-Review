	@Option(name = "--tags"
	private Boolean tags;

	@Option(name = "--no-tags"
	void notags(@SuppressWarnings("unused")
	final boolean ignored) {
		tags = Boolean.FALSE;
	}

