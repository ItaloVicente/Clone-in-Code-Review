	static class StagingViewSearchThread extends Thread {
		private StagingView stagingView;

		private static final Object lock = new Object();

		private volatile static int globalThreadIndex = 0;

		private int currentThreadIx;

		public StagingViewSearchThread(StagingView stagingView) {
			super("staging_view_filter_thread" + ++globalThreadIndex); //$NON-NLS-1$
			this.stagingView = stagingView;
			currentThreadIx = globalThreadIndex;
		}

		public void run() {
			synchronized (lock) {
				if (currentThreadIx < globalThreadIndex) {
					return;
				}
				stagingView.refreshViewers();
			}
		}

	}

