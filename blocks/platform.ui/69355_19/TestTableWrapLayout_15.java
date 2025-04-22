	@Before
	public void setUp() {
		display = PlatformUI.getWorkbench().getDisplay();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		inner = new Composite(shell, SWT.NONE);
		inner.setSize(100, 300);
		layout = new TableWrapLayout();
		layout.leftMargin = 0;
		layout.rightMargin = 0;
		layout.topMargin = 0;
		layout.bottomMargin = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		inner.setLayout(layout);
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

