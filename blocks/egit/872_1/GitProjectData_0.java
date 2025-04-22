		IEclipsePreferences d = new DefaultScope().getNode(Activator.getPluginId());
		IEclipsePreferences p = new InstanceScope().getNode(Activator.getPluginId());
		c.setPackedGitLimit(p.getInt(GitCorePreferences.core_packedGitLimit, d.getInt(GitCorePreferences.core_packedGitLimit, 0)));
		c.setPackedGitWindowSize(p.getInt(GitCorePreferences.core_packedGitWindowSize, d.getInt(GitCorePreferences.core_packedGitWindowSize, 0)));
		c.setPackedGitMMAP(p.getBoolean(GitCorePreferences.core_packedGitMMAP, d.getBoolean(GitCorePreferences.core_packedGitMMAP, false)));
		c.setDeltaBaseCacheLimit(p.getInt(GitCorePreferences.core_deltaBaseCacheLimit, d.getInt(GitCorePreferences.core_deltaBaseCacheLimit, 0)));
