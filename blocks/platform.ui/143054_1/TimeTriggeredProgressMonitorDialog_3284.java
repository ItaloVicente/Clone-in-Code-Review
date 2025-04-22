		return wrapperedMonitor;
	}

	@Override
	public void run(final boolean fork, final boolean cancelable, final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		final InvocationTargetException[] invokes = new InvocationTargetException[1];
		final InterruptedException[] interrupt = new InterruptedException[1];
		Runnable dialogWaitRunnable = () -> {
