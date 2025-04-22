	public <T> T execAndGet(Supplier<T> supplier) {
		Objects.requireNonNull(supplier);

		if (isCurrent()) {
			return supplier.get();
		}

		SafeRunnableBox<T> box = new SafeRunnableBox<>(supplier);
		syncExec(box);

		if (box.isExceptionThrown) {
			if (box.exception instanceof RuntimeException) {
				throw (RuntimeException) box.exception;
			} else if (box.exception instanceof Error) {
				throw (Error) box.exception;
			} else if (box.exception == null) {
				throw new Error("Unknown exception thrown on receiver realm"); //$NON-NLS-1$
			} else {
				throw new Error("Unexpected exception", box.exception); //$NON-NLS-1$
			}
		}

		return box.result;
	}

	private static class SafeRunnableBox<T> implements ISafeRunnable, Runnable {
		private final Supplier<T> supplier;
		private Throwable exception = null;
		private boolean isExceptionThrown = true;
		private T result;

		private SafeRunnableBox(Supplier<T> supplier) {
			this.supplier = supplier;
		}

		@Override
		public void run() {
			result = supplier.get();
			isExceptionThrown = false;
		}

		@Override
		public void handleException(Throwable exc) {
			this.exception = exc;
		}
	}

