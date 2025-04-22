	/**
	 * Work-around for bug 565394 / SSHD-1050; remove when using sshd 2.6.0.
	 */
	private volatile AuthFuture authFuture;

	/** Records exceptions before there is an authFuture. */
	private List<Throwable> earlyErrors = new ArrayList<>();

	/** Guards setting an earlyError and the authFuture together. */
	private final Object errorLock = new Object();

