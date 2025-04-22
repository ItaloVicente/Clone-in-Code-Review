	@Override
	protected void performDefaults() {
		super.performDefaults();
		enableDependentFields(monitoringEnabled.getBooleanValue());
	}

	private void enableDependentFields(boolean enable) {
		for (Map.Entry<FieldEditor, Composite> entry : editors.entrySet()) {
			FieldEditor editor = entry.getKey();
			if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
				editor.setEnabled(enable, entry.getValue());
			}
		}
	}

