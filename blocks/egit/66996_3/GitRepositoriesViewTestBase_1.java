
	private static class TimeoutProgressMonitor extends NullProgressMonitor {

		private final long stopTime;

		public TimeoutProgressMonitor(long timeUnits, TimeUnit timeUnit) {
			stopTime = System.currentTimeMillis()
					+ timeUnit.toMillis(timeUnits);
		}

		@Override
		public boolean isCanceled() {
			boolean canceled = super.isCanceled();
			if (canceled) {
				return true;
			}
			setCanceled(System.currentTimeMillis() > stopTime);
			return super.isCanceled();
		}
	}
