	}

	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		if (!fork) {
			enableDetails(false);
		}
		super.run(fork, cancelable, runnable);
	}

	protected void enableDetails(boolean enableState) {
		if (detailsButton == null) {
