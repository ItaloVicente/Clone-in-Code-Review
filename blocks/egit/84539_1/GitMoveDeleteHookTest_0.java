	private void configureAutoStageMoves(boolean autoStageMoves) {
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		p.putBoolean(GitCorePreferences.core_autoStageMoves, autoStageMoves);
	}

	@Theory
	public void testMoveFile(boolean autoStageMoves) throws Exception {
		configureAutoStageMoves(autoStageMoves);

