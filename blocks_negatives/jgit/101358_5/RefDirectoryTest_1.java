
	private static final class StrictWorkMonitor implements ProgressMonitor {
		private int lastWork, totalWork;

		@Override
		public void start(int totalTasks) {
		}

		@Override
		public void beginTask(String title, int total) {
			this.totalWork = total;
			lastWork = 0;
		}

		@Override
		public void update(int completed) {
			lastWork += completed;
		}

		@Override
		public void endTask() {
			assertEquals("Units of work recorded", totalWork, lastWork);
		}

		@Override
		public boolean isCancelled() {
			return false;
		}
	}
