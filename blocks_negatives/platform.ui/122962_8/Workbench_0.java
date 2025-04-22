		private final List<String> starting;

		StartupProgressBundleListener(IProgressMonitor progressMonitor, int maximumProgressCount) {
			super();
			this.progressMonitor = progressMonitor;
			this.maximumProgressCount = maximumProgressCount;
			this.starting = new ArrayList<>();
