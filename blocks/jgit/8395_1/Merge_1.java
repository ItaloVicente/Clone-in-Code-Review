	@Option(name = "--ff")
	private boolean ff = true;

	@Option(name = "--no-ff")
	void nothin(@SuppressWarnings("unused") final boolean ignored) {
		ff = false;
	}

