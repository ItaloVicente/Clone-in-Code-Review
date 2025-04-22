				getFieldEditorParent()) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}
		};
		addField(useLogicalModelEditor);
		modelStrategyEditor = new RadioGroupFieldEditor(
				GitCorePreferences.core_preferredModelMergeStrategy,
				UIText.GitPreferenceRoot_preferreModelMergeStrategy, 1,
				getAvailableMergeStrategies(), getFieldEditorParent(), true) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getCorePreferenceStore();
			}
		};
		addField(modelStrategyEditor);
	}

	private String[][] getAvailableMergeStrategies() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(
						"org.eclipse.egit.core.modelMergeStrategy"); //$NON-NLS-1$
		List<String[]> strategies = new ArrayList<>();
		strategies.add(new String[] {
				UIText.GitPreferenceRoot_defaultMergeStrategyLabel,
				MergeStrategy.RECURSIVE.getName() });
		for (IConfigurationElement element : elements) {
			try {
				Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (ext instanceof MergeStrategy) {
					strategies.add(new String[] {
							element.getAttribute("label"), //$NON-NLS-1$
							((MergeStrategy) ext).getName() });
				}
			} catch (CoreException e) {
				Activator
						.logError(
								UIText.GitPreferenceRoot_modelMergeStrategyLoadError,
								e);
			}
		}
		return strategies.toArray(new String[strategies.size()][2]);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() == useLogicalModelEditor) {
			modelStrategyEditor.setEnabled(
					((Boolean) event.getNewValue()).booleanValue(),
					getFieldEditorParent());
		}
	}

	private ScopedPreferenceStore getCorePreferenceStore() {
		if (corePreferenceStore == null) {
			corePreferenceStore = new ScopedPreferenceStore(
					InstanceScope.INSTANCE, Activator.getPluginId());
		}
		return corePreferenceStore;
