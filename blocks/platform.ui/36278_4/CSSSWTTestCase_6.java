
	@Before
	public void setUp() {
		display = Display.getDefault();
	}

	@After
	public void tearDown() {
		display = Display.getDefault();
		if (!display.isDisposed()) {
			for (Shell shell : display.getShells()) {
				shell.dispose();
			}
			display.dispose();
