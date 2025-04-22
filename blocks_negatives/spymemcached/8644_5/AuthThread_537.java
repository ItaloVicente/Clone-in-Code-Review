	private final MemcachedConnection conn;
	private final AuthDescriptor authDescriptor;
	private final OperationFactory opFact;
	private final MemcachedNode node;

	public AuthThread(MemcachedConnection c, OperationFactory o,
			AuthDescriptor a, MemcachedNode n) {
		conn = c;
		opFact = o;
		authDescriptor = a;
		node = n;
		start();
	}

	@Override
	public void run() {
		OperationStatus priorStatus = null;
		final AtomicBoolean done = new AtomicBoolean();
