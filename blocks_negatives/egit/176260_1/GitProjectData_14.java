	/**
	 * Update the settings for the global window cache of the workspace.
	 */
	public static void reconfigureWindowCache() {
		final WindowCacheConfig c = new WindowCacheConfig();
		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		c.setPackedGitLimit(p.getInt(GitCorePreferences.core_packedGitLimit, d.getInt(GitCorePreferences.core_packedGitLimit, 0)));
		c.setPackedGitWindowSize(p.getInt(GitCorePreferences.core_packedGitWindowSize, d.getInt(GitCorePreferences.core_packedGitWindowSize, 0)));
		if (SystemReader.getInstance().isWindows()) {
			c.setPackedGitMMAP(false);
		} else {
			c.setPackedGitMMAP(
					p.getBoolean(GitCorePreferences.core_packedGitMMAP,
							d.getBoolean(GitCorePreferences.core_packedGitMMAP,
									false)));
		}
		c.setDeltaBaseCacheLimit(p.getInt(GitCorePreferences.core_deltaBaseCacheLimit, d.getInt(GitCorePreferences.core_deltaBaseCacheLimit, 0)));
		c.setStreamFileThreshold(p.getInt(GitCorePreferences.core_streamFileThreshold, d.getInt(GitCorePreferences.core_streamFileThreshold, 0)));
		c.setExposeStatsViaJmx(false);
		c.install();
	}

