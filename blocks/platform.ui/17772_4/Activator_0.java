	private ServiceTracker<DebugOptions, DebugOptions> debugTracker;
	private ServiceTracker<LogService, LogService> logTracker;

	private BundleTracker<List<Bundle>> resolvedBundles;

	private final BundleFinder bundleFinder = new BundleFinder();
