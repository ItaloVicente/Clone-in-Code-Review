	public <T> T execAndGet(Supplier<T> supplier) {
		SafeRunnableBox<T> box = new SafeRunnableBox<>(supplier);
		if (isCurrent()) {
			safeRun(box);
		} else {
			syncExec(box);
		}
		return box.result;
	}

	private static class SafeRunnableBox<T> implements ISafeRunnable, Runnable {
		private Supplier<T> supplier;
		private T result;

		private SafeRunnableBox(Supplier<T> supplier) {
			this.supplier = supplier;
		}

		@Override
		public void run() {
			result = supplier.get();
		}

		@Override
		public void handleException(Throwable exception) {
			if (supplier instanceof ISafeRunnable) {
				((ISafeRunnable) supplier).handleException(exception);
			} else {
				logException(exception);
			}
		}
	}

