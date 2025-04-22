	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "perf_basic", BasicPerformanceTest.NONE },
				{ "perf_outline", BasicPerformanceTest.NONE }, { "java", BasicPerformanceTest.LOCAL } });
	}


