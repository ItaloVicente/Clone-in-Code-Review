	private volatile AuthFuture authFuture;

	private List<Throwable> earlyErrors = new ArrayList<>();

	private final Object errorLock = new Object();

