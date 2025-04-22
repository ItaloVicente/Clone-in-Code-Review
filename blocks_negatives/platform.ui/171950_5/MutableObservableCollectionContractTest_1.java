
	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableCollectionContractTest.class, delegate)
				.addObservableContractTest(
						ObservableCollectionContractTest.class, delegate)
				.build();
	}
