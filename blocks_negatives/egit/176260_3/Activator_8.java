		final IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode(PLUGIN_ID);
		String preferredMergeStrategyKey = prefs.get(
				GitCorePreferences.core_preferredMergeStrategy, null);

		if (preferredMergeStrategyKey == null
				|| preferredMergeStrategyKey.isEmpty()) {
			final IEclipsePreferences defaultPrefs = DefaultScope.INSTANCE
					.getNode(PLUGIN_ID);
			preferredMergeStrategyKey = defaultPrefs.get(
					GitCorePreferences.core_preferredMergeStrategy, null);
		}
		if (preferredMergeStrategyKey != null
				&& !preferredMergeStrategyKey.isEmpty()
