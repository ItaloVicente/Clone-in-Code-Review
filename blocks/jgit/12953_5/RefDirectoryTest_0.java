
	private static final class StrictWorkMonitor implements ProgressMonitor {
		private int lastWork

		public void start(int totalTasks) {
		}

		public void beginTask(String title
			this.totalWork = totalWork;
			lastWork = 0;
		}

		public void update(int completed) {
			lastWork += completed;
		}

		public void endTask() {
			assertEquals("Units of work recorded"
		}

		public boolean isCancelled() {
			return false;
		}
	}
