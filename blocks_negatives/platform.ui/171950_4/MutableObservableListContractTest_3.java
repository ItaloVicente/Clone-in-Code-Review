
	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableListContractTest.class, delegate)
				.addObservableContractTest(ObservableListContractTest.class,
						delegate).build();
	}
