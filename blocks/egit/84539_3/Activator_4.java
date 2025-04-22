	public static boolean autoStageMoves() {
		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.getPluginId());
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		boolean autoStageMoves = p.getBoolean(
				GitCorePreferences.core_autoStageMoves,
				d.getBoolean(GitCorePreferences.core_autoStageMoves, false));
		return autoStageMoves;
	}
