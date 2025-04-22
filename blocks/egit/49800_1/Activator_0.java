	public MergeStrategy getPreferredMergeStrategy() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		if (prefs.getBoolean(GitCorePreferences.core_enableLogicalModel, false)) {
			String preferredMergeStrategyKey = prefs.get(
					GitCorePreferences.core_preferredModelMergeStrategy,
					MergeStrategy.RECURSIVE.getName());
			if (preferredMergeStrategyKey != null
					&& !preferredMergeStrategyKey.isEmpty()) {
				MergeStrategy result = MergeStrategy
						.get(preferredMergeStrategyKey);
				if (result != null) {
					return result;
				}
				logError(NLS.bind(
						CoreText.Activator_invalidPreferredModelMergeStrategy,
						preferredMergeStrategyKey), null);
			}
			logError(CoreText.Activator_missingPreferredModelMergeStrategy,
					null);
		}
		return MergeStrategy.RECURSIVE;
	}

