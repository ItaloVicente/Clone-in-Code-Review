	private static final String DIFF_TOOL = CONFIG_DIFFTOOL_SECTION;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		configureEchoTool(TOOL_NAME);
