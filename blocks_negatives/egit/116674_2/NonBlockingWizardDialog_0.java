	/**
	 * If {@code fork} is {@code true}, this implementation does <em>not</em>
	 * block but schedules a true background job. Such background jobs are
	 * queued and will execute one after another. They are canceled when the
	 * current wizard page changes, or when the dialog closes.
	 * </p>
	 */
	@Override
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		if (!fork) {
			super.run(fork, cancelable, runnable);
		}
		run(runnable, null);
	}

