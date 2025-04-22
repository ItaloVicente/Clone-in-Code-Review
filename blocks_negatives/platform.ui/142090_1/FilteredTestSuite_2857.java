    private String filterTestClassName;
    private String filterTestName;

    public FilteredTestSuite() {
    	BundleContext context = UIPerformancePlugin.getDefault().getContext();
    		System.err.println("UIPerformanceTestSuite was unable to retirieve bundle context; test filtering is disabled");
    		return;
    	}
