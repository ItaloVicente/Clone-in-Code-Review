
	public static Test suite(IObservableValueContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						SWTMutableObservableValueContractTest.class, delegate)
				.addObservableContractTest(
						SWTObservableValueContractTest.class, delegate).build();
	}
