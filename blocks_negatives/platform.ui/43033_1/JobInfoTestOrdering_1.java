	/*
	 * ========================================================================
	 */

	/**
	 * Only provides better readable {@link #toString()} method.
	 */
	private static class ExtendedJobInfo extends JobInfo {

		public ExtendedJobInfo(Job enclosingJob) {
			super(enclosingJob);
		}

		@Override
		public String toString() {
			return "ExtendedJobInfo [getName()=" + getJob().getName() + ", getPriority()="
						+ getJob().getPriority() + ", getState()=" + getJob().getState()
						+ ", isSystem()=" + getJob().isSystem() + ", isUser()=" + getJob().isUser()
						+ "]";
		}

	}

	/**
	 * Enables access to internal state, by using reflection
	 * Provides better readable {@link #toString()} method.
	 */
	private static class TestJob extends Job {

		public TestJob(String name) {
			super(name);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			throw new UnsupportedOperationException("Not implemented, because of just a unit test");
		}

		public void setInternalJobState(int state) {
			try {
				final Field field = InternalJob.class.getDeclaredField("flags");
				field.set(this, new Integer(state));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}
