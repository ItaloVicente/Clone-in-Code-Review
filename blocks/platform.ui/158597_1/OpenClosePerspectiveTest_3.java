	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { EmptyPerspective.PERSP_ID2, BasicPerformanceTest.NONE }, {
				UIPerformanceTestSetup.PERSPECTIVE1,
				BasicPerformanceTest.LOCAL },
				{ "org.eclipse.ui.resourcePerspective", BasicPerformanceTest.NONE },
				{ "org.eclipse.jdt.ui.JavaPerspective", BasicPerformanceTest.NONE },
				{ "org.eclipse.debug.ui.DebugPerspective", BasicPerformanceTest.NONE } });
	}

