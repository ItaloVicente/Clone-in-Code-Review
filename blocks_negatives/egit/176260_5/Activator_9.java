		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		boolean autoStageDeletion = p.getBoolean(
				GitCorePreferences.core_autoStageDeletion,
				d.getBoolean(GitCorePreferences.core_autoStageDeletion, false));
		return autoStageDeletion;
