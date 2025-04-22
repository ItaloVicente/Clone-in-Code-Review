	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { EmptyPerspective.PERSP_ID2 }, { UIPerformanceTestSetup.PERSPECTIVE1 },
				{ "org.eclipse.ui.resourcePerspective" }, { "org.eclipse.jdt.ui.JavaPerspective" },
				{ "org.eclipse.debug.ui.DebugPerspective" } });
	}

	public OpenCloseWindowTest(String id) {
		super("testOpenCloseWindows:" + id, BasicPerformanceTest.NONE);
