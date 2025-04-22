	private int refsChangedEvents;

	private ListenerHandle handle;

	private RefsChangedListener refsChangedListener = event -> {
		refsChangedEvents++;
	};

