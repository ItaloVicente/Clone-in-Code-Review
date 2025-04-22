	@Option(name = "-M"
	private boolean detectRenames;

	@Option(name = "--name-status"
	private boolean showNameAndStatusOnly;

	@Option(name = "-p"
	private boolean showPatch;

	@Option(name = "-U"
	void unified(int lines) {
		diffFmt.setContext(lines);
	}

	private DiffFormatter diffFmt = new DiffFormatter();

