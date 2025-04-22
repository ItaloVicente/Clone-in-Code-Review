	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;
	private final boolean daemon;

	public BasicThreadFactory(String name, boolean daemon) {
		this.namePrefix = name + "-" + poolNumber.getAndIncrement() + "-";
		this.daemon = daemon;
	}
