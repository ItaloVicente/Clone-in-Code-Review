	@Option(name = "--ff")
	private FastForwardMode ff = FastForwardMode.FF;

	@Option(name = "--no-ff")
	void noff(@SuppressWarnings("unused") final boolean ignored) {
		ff = FastForwardMode.NO_FF;
	}

	@Option(name = "--ff-only")
	void ffonly(@SuppressWarnings("unused") final boolean ignored) {
		ff = FastForwardMode.FF_ONLY;
	}

