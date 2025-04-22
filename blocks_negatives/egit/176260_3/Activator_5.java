	private AutoShareProjects shareGitProjectsJob;
	private IResourceChangeListener preDeleteProjectListener;
	private IgnoreDerivedResources ignoreDerivedResourcesListener;
	private MergeStrategyRegistryListener mergeStrategyRegistryListener;
	private IPreferenceChangeListener preferenceChangeListener;
	private ServiceTracker<IProxyService, IProxyService> proxyServiceTracker;
	private ListenerHandle refreshHandle;
