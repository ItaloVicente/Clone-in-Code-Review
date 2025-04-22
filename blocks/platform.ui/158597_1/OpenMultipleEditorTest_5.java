	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "perf_basic", true }, { "perf_outline", true }, { "java", true },
				{ "perf_basic", false }, { "perf_outline", false }, { "java", false } });
	}

	public OpenMultipleEditorTest(String extension, boolean closeAll) {
		super("testOpenMultipleEditors:" + extension + (closeAll ? "[closeAll]" : "[closeEach]"),
				BasicPerformanceTest.NONE);
