				getFieldEditorParent()) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}
		};
		addField(useLogicalModelEditor);

		Label spacer = new Label(getFieldEditorParent(), SWT.NONE);
		spacer.setSize(0, 12);
		Composite modelStrategyParent = getFieldEditorParent();
		modelStrategyEditor = new RadioGroupFieldEditor(
				GitCorePreferences.core_preferredMergeStrategy,
				UIText.GitPreferenceRoot_preferreMergeStrategy_group, 1,
				getAvailableMergeStrategies(), modelStrategyParent, false) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}

		};
		modelStrategyEditor.getLabelControl(modelStrategyParent)
				.setToolTipText(UIText.GitPreferenceRoot_preferreMergeStrategy_label);
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
