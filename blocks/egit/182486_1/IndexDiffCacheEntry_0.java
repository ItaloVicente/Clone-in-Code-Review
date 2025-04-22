	private static abstract class IndexDiffReloadJob extends Job {

		private volatile boolean started;

		protected IndexDiffReloadJob(String name) {
			super(name);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			started = true;
			return reload(monitor);
		}

		protected abstract IStatus reload(IProgressMonitor monitor);

		protected boolean isPending() {
			return !started;
		}
	}
