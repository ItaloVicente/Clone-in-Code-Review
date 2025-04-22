		gitCorePreferences = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		GitHosts.loadFromPreferences(gitCorePreferences);
		prefsListener = event -> {
			if (GitCorePreferences.core_gitServers.equals(event.getKey())) {
				GitHosts.loadFromPreferences(gitCorePreferences);
			}
		};
		gitCorePreferences.addPreferenceChangeListener(prefsListener);
