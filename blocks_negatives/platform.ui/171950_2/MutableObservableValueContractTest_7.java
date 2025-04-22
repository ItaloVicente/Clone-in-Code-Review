
	public static Test suite(IObservableValueContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableValueContractTest.class, delegate)
				.addObservableContractTest(ObservableValueContractTest.class,
						delegate).build();
	}
