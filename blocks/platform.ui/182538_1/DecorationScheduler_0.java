	private final class JobChangeListener extends JobChangeAdapter {
		boolean waiting;

		@Override
		public void done(IJobChangeEvent event) {
			synchronized (this) {
				if (waiting) {
					jobFinishListener.notifyAll();
				}
			}
		}

		void sleep(long timeoutMillis) throws InterruptedException {
			synchronized (this) {
				waiting = true;
				this.wait(timeoutMillis);
				waiting = false;
			}
		}
	}

	private final JobChangeListener jobFinishListener = new JobChangeListener();

