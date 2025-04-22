	public MergeStrategy getPreferredMergeStrategy() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode(Activator.getPluginId());
		String preferredMergeStrategyKey = prefs.get(
				GitCorePreferences.core_preferredMergeStrategy, null);
		if (preferredMergeStrategyKey != null
				&& !preferredMergeStrategyKey.isEmpty()) {
			MergeStrategy result = MergeStrategy.get(preferredMergeStrategyKey);
			if (result != null) {
				return result;
			}
			logError(NLS.bind(CoreText.Activator_invalidPreferredMergeStrategy,
					preferredMergeStrategyKey), null);
		}
		return null;
	}

	public Collection<MergeStrategyDescriptor> getRegisteredMergeStrategies() {
		return mergeStrategyRegistryListener.getStrategies();
	}

	private void registerMergeStrategyRegistryListener() {
		mergeStrategyRegistryListener = new MergeStrategyRegistryListener(
				Platform.getExtensionRegistry());
		Platform.getExtensionRegistry().addListener(
				mergeStrategyRegistryListener,
				"org.eclipse.egit.core.mergeStrategy"); //$NON-NLS-1$
	}

