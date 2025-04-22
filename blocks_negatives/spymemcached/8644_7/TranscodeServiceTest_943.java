	private TranscodeService ts = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ts = new TranscodeService(false);
	}
