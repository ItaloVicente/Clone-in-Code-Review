	@Theory
	public void testDeleteFile(boolean autoStageDelete) throws Exception {
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		p.putBoolean(GitCorePreferences.core_autoStageDeletion,
				autoStageDelete);

