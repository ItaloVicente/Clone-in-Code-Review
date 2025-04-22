
	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableSetContractTest.class, delegate)
				.addObservableContractTest(
						ObservableCollectionContractTest.class, delegate)
				.build();
	}
