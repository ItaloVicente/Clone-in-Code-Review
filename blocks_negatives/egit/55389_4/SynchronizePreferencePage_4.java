
		Label spacer = new Label(getFieldEditorParent(), SWT.NONE);
		spacer.setSize(0, 12);
		Composite modelStrategyParent = getFieldEditorParent();
		preferredMergeStrategyEditor = new RadioGroupFieldEditor(
				GitCorePreferences.core_preferredMergeStrategy,
				UIText.GitPreferenceRoot_preferreMergeStrategy_group, 1,
				getAvailableMergeStrategies(), modelStrategyParent, false);
		preferredMergeStrategyEditor.getLabelControl(modelStrategyParent)
				.setToolTipText(
						UIText.GitPreferenceRoot_preferreMergeStrategy_label);
		addField(preferredMergeStrategyEditor);
	}

	@Override
	protected void initialize() {
		super.initialize();
		preferredMergeStrategyEditor
				.setPreferenceStore(getCorePreferenceStore());
		preferredMergeStrategyEditor.load();
	}

	private String[][] getAvailableMergeStrategies() {
		org.eclipse.egit.core.Activator coreActivator = org.eclipse.egit.core.Activator
				.getDefault();
		List<String[]> strategies = new ArrayList<>();

		strategies.add(new String[] {
				UIText.GitPreferenceRoot_defaultMergeStrategyLabel,
				GitCorePreferences.core_preferredMergeStrategy_Default });
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
	}

	@Override
	public boolean performOk() {
		if (super.performOk()) {
			if (getCorePreferenceStore().needsSaving()) {
				try {
					getCorePreferenceStore().save();
				} catch (IOException e) {
					String message = JFaceResources
							.format("PreferenceDialog.saveErrorMessage", new Object[] { //$NON-NLS-1$
									getTitle(), e.getMessage() });
					Policy.getStatusHandler()
							.show(new Status(IStatus.ERROR, Policy.JFACE,
									message, e),
									JFaceResources

				}
			}
		}
		return true;
