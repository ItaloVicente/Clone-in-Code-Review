	private void createBooleanFieldEditor(String name, String labelText, Composite parent) {
		BooleanFieldEditor field = new BooleanFieldEditor(name, labelText, parent);
		field.fillIntoGrid(parent, 2);
		addField(field, parent);
		if (!name.equals(PreferenceConstants.MONITORING_ENABLED)) {
			field.setEnabled(pluginEnabled, parent);
		}
