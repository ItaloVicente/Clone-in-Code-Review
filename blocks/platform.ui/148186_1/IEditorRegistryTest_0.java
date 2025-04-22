	private final AtomicInteger loggedErrors = new AtomicInteger();

	private final ILogListener errorLogListener = (IStatus status, String plugin) -> {
		if (status.matches(IStatus.ERROR)) {
			System.out.println(status);
			loggedErrors.incrementAndGet();
		}
	};

