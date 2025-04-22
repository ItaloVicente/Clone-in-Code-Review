	private <T extends FieldEditor> T addField(T editor, Composite parent) {
		super.addField(editor);
		editor.fillIntoGrid(parent, 2);
		editors.put(editor, parent);
		if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
			editor.setEnabled(pluginEnabled, parent);
		}
		return editor;
