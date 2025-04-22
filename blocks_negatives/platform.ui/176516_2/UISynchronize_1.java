	public void busyExec(Runnable action) throws InterruptedException {
		try {
			busyCall(() -> {
				action.run();
				return null;
			});
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			throw new RuntimeException(e);
		}
