	@Override
	protected void initialize() {
		super.initialize();
		useStagingView.setPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (FieldEditor.VALUE.equals(event.getProperty())) {
					autoStage.setEnabled(
							((Boolean) event.getNewValue()).booleanValue(),
							getFieldEditorParent());
				}
			}
		});
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		autoStage.setEnabled(
				getPreferenceStore().getDefaultBoolean(
						UIPreferences.ALWAYS_USE_STAGING_VIEW),
				getFieldEditorParent());
	}

