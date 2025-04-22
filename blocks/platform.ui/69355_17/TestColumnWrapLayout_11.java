	private Display display;
	private Shell shell;
	private Composite inner;
	private ColumnLayout layout;

	@Before
	public void setUp() {
		display = PlatformUI.getWorkbench().getDisplay();
		shell = new Shell(display);
		inner = new Composite(shell, SWT.NULL);
		inner.setSize(100, 300);
		layout = new ColumnLayout();
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

