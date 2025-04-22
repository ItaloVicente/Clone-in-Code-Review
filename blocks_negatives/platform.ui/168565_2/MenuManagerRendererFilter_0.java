	private class SafeWrapper implements ISafeRunnable {
		Event event;

		@Override
		public void handleException(Throwable e) {
			if (e instanceof Error) {
				throw (Error) e;
			}
			if (logger != null) {
				logger.error(e);
			}
		}

		@Override
		public void run() throws Exception {
			safeHandleEvent(event);
		}
	}

	private SafeWrapper safeWrapper = new SafeWrapper();

