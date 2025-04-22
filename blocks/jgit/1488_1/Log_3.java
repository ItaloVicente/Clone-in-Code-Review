	private Boolean detectRenames;

	@Option(name = "--no-renames"
	void noRenames(@SuppressWarnings("unused") boolean on) {
		detectRenames = Boolean.FALSE;
	}
