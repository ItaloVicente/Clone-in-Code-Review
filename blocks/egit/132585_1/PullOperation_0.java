	private static class PullJobGroup extends JobGroup {
		public PullJobGroup(String name, int maxThreads, int initialJobCount) {
			super(name, maxThreads, initialJobCount);
		}

		@Override
		protected boolean shouldCancel(IStatus lastCompletedJobResult,
				int numberOfFailedJobs, int numberOfCancelledJobs) {
			return false;
		}
	}

