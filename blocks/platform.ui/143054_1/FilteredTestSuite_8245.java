	public FilteredTestSuite() {
		BundleContext context = UIPerformancePlugin.getDefault().getContext();
		if (context == null) { // most likely run in a wrong launch mode
			System.err.println("UIPerformanceTestSuite was unable to retirieve bundle context; test filtering is disabled");
			return;
		}
