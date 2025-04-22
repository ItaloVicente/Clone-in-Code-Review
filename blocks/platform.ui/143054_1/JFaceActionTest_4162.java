	private Display display;
	private Shell shell;

	protected JFaceActionTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		display = Display.getCurrent();
		if (display == null) {
			display = new Display();
		}
		shell = new Shell(display);
		shell.setSize(500, 500);
		shell.setLayout(new FillLayout());
		shell.open();
	}

	@Override
	protected void tearDown() throws Exception {
		shell.dispose();
	}

	protected Display getDisplay() {
		return display;
	}

	protected Shell getShell() {
		return shell;
	}
