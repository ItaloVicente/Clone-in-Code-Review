	@Option(name = "--backup"
			"-b" }
	private boolean backup = true;

	@Option(name = "--reflogs"
			"-r" }
	private boolean writeLogs = true;

	@Override
	protected void run() throws Exception {
		((FileRepository) db).convertRefStorage(format
	}
