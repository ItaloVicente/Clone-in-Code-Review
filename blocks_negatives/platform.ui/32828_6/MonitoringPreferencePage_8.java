	private void addField(FieldEditor editor, Composite parent) {
		super.addField(editor);

		if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
			editors.put(editor, parent);
		}
