	private void addWindowListener() {
		windowListener = new TestWindowListener();
		fWorkbench.addWindowListener(windowListener);
	}

	private void removeWindowListener() {
		if (windowListener != null) {
			fWorkbench.removeWindowListener(windowListener);
		}
	}

	protected void trace(String msg) {
		System.out.println(msg);
	}

	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
