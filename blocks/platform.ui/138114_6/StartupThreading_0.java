	public static <T> T runWithoutExceptions(Callable<T> r) throws RuntimeException {
		final AtomicReference<T> result = new AtomicReference<>();
		runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() throws Throwable {
				result.set(r.call());
			}
		});
		return result.get();
	}
