		return pm.getUpdatesCount();
	}

	private static class UpdateCounter implements ProgressMonitor {
		private final ProgressMonitor delegate;

		private int updates = 0;

		private UpdateCounter(ProgressMonitor pm) {
			this.delegate = pm;
		}

		public void start(int totalTasks) {
			delegate.start(totalTasks);
		}

		public void beginTask(String title
			delegate.beginTask(title
		}

		public void update(int completed) {
			synchronized (this) {
				updates += completed;
			}
		}

		public void endTask() {
			delegate.endTask();
		}

		public boolean isCancelled() {
			return delegate.isCancelled();
		}

		public int getUpdatesCount() {
			return updates;
		}
