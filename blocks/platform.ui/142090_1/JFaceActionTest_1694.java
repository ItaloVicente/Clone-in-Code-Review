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
