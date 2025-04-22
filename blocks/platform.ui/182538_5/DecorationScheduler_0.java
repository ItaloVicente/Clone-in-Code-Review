	private final class JobChangeListener extends JobChangeAdapter {

		@Override
		public void done(IJobChangeEvent event) {
			synchronized (this) {
				if (!updatesPending()) { // signal only if no more updates pending
					this.notifyAll(); // also notify if nobody is waiting.
				}
			}
		}

		void sleep(long timeoutMillis) throws InterruptedException {
			synchronized (this) {
				if (updatesPending()) { // avoid wait if no updates pending
					this.wait(timeoutMillis);
				}
			}
		}
	}

	private final JobChangeListener jobFinishListener = new JobChangeListener();

