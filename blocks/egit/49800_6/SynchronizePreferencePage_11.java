				getFieldEditorParent()) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}
		};
		addField(useLogicalModelEditor);
		modelStrategyEditor = new RadioGroupFieldEditor(
				GitCorePreferences.core_preferredMergeStrategy,
				UIText.GitPreferenceRoot_preferreMergeStrategy, 1,
				getAvailableMergeStrategies(), getFieldEditorParent(), true) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}
		};
		addField(modelStrategyEditor);
	}

	private String[][] getAvailableMergeStrategies() {
		org.eclipse.egit.core.Activator coreActivator = org.eclipse.egit.core.Activator
				.getDefault();
		List<String[]> strategies = new ArrayList<>();
		strategies.add(new String[] {
				UIText.GitPreferenceRoot_defaultMergeStrategyLabel, "" }); //$NON-NLS-1$
		for (MergeStrategyDescriptor strategy : coreActivator
				.getRegisteredMergeStrategies()) {
			strategies.add(new String[] { strategy.getLabel(),
					strategy.getName() });
		}
		return strategies.toArray(new String[strategies.size()][2]);
	}

	private ScopedPreferenceStore getCorePreferenceStore() {
		if (corePreferenceStore == null) {
			corePreferenceStore = new ScopedPreferenceStore(
					InstanceScope.INSTANCE,
					org.eclipse.egit.core.Activator.getPluginId());
		}
		return corePreferenceStore;
