	/**
	 * @param delegate
	 */
	public MutableObservableValueContractTest(
			IObservableValueContractDelegate delegate) {
		this(null, delegate);
	}

	public MutableObservableValueContractTest(String testName,
			IObservableValueContractDelegate delegate) {
		super(testName, delegate);
