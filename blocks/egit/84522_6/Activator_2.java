	public static boolean autoStageDeletion() {
		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.getPluginId());
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		boolean autoStageDeletion = p.getBoolean(
				GitCorePreferences.core_autoStageDeletion,
				d.getBoolean(GitCorePreferences.core_autoStageDeletion, false));
		return autoStageDeletion;
	}

