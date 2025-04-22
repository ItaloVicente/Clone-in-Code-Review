	@BeforeClass
	public static void setUpClass() {
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
				.getPluginId());
		p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources, false);
		p.putBoolean(GitCorePreferences.core_autoShareProjects, false);
	}

