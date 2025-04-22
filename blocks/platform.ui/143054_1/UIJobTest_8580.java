		super.doSetUp();
		fWindow = openTestWindow();
		fPage = fWindow.getActivePage();
	}

	private volatile boolean uiJobFinished = false;
	private volatile boolean backgroundThreadStarted = false;
	private volatile boolean backgroundThreadInterrupted = false;
	private volatile boolean backgroundThreadFinishedBeforeUIJob = false;
	private volatile boolean backgroundThreadFinished = false;
	private volatile boolean uiJobFinishedBeforeBackgroundThread = false;

	public void testJoin() throws Exception {

		uiJobFinished = false;
		backgroundThreadStarted = false;
		backgroundThreadFinished = false;
		backgroundThreadInterrupted = false;
		uiJobFinishedBeforeBackgroundThread = false;

		final UIJob testJob = new UIJob("blah blah blah") {
			@Override
