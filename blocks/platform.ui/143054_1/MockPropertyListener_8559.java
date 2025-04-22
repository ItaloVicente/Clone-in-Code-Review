	private CallHistory callTrace;

	private Object sourceMask;

	private int sourceId;

	public MockPropertyListener(Object source, int id) {
		sourceMask = source;
		sourceId = id;
		callTrace = new CallHistory(this);
	}

	@Override
