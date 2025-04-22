	public static boolean autoIgnoreDerived() {
		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.getPluginId());
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		return p.getBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
				d.getBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
						true));
	}

