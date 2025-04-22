	@BeforeEach
	private void testInfo(TestInfo testInfo) {
		Optional<Method> testMethod = testInfo.getTestMethod();
		if (testMethod.isPresent()) {
			this.testName = testMethod.get().getName();
		}
	}

	@BeforeEach
