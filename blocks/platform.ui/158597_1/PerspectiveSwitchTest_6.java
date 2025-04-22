	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { // Test switching between the two most commonly used perspectives in the
				{ "org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.debug.ui.DebugPerspective", "1.java" },

				{ UIPerformanceTestSetup.PERSPECTIVE1, UIPerformanceTestSetup.PERSPECTIVE2, "1.perf_basic" },

				{ "org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.ui.tests.util.EmptyPerspective", "1.perf_basic" },

				{ "org.eclipse.ui.resourcePerspective", "org.eclipse.jdt.ui.JavaPerspective", "1.java" } });
	}

	public PerspectiveSwitchTest(String id1, String id2, String activeEditor) {
		super("testPerspectiveSwitch:" + id1 + "," + id2 + ",editor " + activeEditor, BasicPerformanceTest.NONE);
		this.id1 = id1;
		this.id2 = id2;
		this.activeEditor = activeEditor;
