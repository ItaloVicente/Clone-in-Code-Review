
		if (preferredMergeStrategyKey == null
				|| preferredMergeStrategyKey.isEmpty()) {
			final IEclipsePreferences defaultPrefs = DefaultScope.INSTANCE
					.getNode(Activator.getPluginId());
			preferredMergeStrategyKey = defaultPrefs.get(
					GitCorePreferences.core_preferredMergeStrategy, null);
		}
