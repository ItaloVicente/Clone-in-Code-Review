
	private List<IStatus> reportedErrors;
	private ILogListener listener;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reportedErrors = new ArrayList<>();
		listener = new ILogListener() {

			@Override
			public void logging(IStatus status, String plugin) {
				reportedErrors.add(status);
			}
		};
		WorkbenchPlugin.getDefault().getLog().addLogListener(listener);
	}

	@Override
	protected void tearDown() throws Exception {
		if (listener != null) {
			WorkbenchPlugin.getDefault().getLog().removeLogListener(listener);
		}
		super.tearDown();
	}

