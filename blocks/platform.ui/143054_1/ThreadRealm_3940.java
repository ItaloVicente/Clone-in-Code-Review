	private Thread thread;

	private final LinkedList<Runnable> queue = new LinkedList<Runnable>();

	private volatile boolean block;

	public synchronized void init(Thread thread) {
		if (thread == null) {
			throw new IllegalArgumentException("Parameter thread was null."); //$NON-NLS-1$
		}
		Assert.isTrue(this.thread == null, "Realm can only be initialized once.");

		this.thread = thread;
	}

	@Override
