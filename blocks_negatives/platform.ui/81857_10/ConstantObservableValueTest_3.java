
		public static Test suite(IObservableValueContractDelegate delegate) {
			return new SuiteBuilder().addObservableContractTest(
					UnchangeableObservableValueContractTest.class, delegate)
					.build();
		}
